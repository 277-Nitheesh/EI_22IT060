package com.astronaut.scheduler.scheduler;

import com.astronaut.scheduler.app.AppLogger;
import com.astronaut.scheduler.exceptions.TaskConflictException;
import com.astronaut.scheduler.exceptions.TaskNotFoundException;
import com.astronaut.scheduler.model.Task;
import com.astronaut.scheduler.model.TaskPriority;
import com.astronaut.scheduler.observer.ScheduleObserver;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Singleton ScheduleManager that stores tasks and provides thread-safe operations.
 * Observers are notified on adds/removes/updates and on conflicts.
 */
public final class ScheduleManager {
    private static volatile ScheduleManager instance;
    private final Map<String, Task> tasks = new HashMap<>(); // key = description (unique)
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<ScheduleObserver> observers = new CopyOnWriteArrayList<>();
    private final AppLogger logger = AppLogger.getInstance();

    private ScheduleManager() {}

    public static ScheduleManager getInstance() {
        if (instance == null) {
            synchronized (ScheduleManager.class) {
                if (instance == null) instance = new ScheduleManager();
            }
        }
        return instance;
    }

    public void attachObserver(ScheduleObserver o) {
        if (o == null) return;
        observers.add(o);
    }

    public void detachObserver(ScheduleObserver o) {
        observers.remove(o);
    }

    private void notifyAdded(Task t) {
        for (ScheduleObserver o : observers) o.onTaskAdded(t);
    }
    private void notifyRemoved(Task t) {
        for (ScheduleObserver o : observers) o.onTaskRemoved(t);
    }
    private void notifyUpdated(Task t) {
        for (ScheduleObserver o : observers) o.onTaskUpdated(t);
    }
    private void notifyConflict(Task attempted, Task conflicting, String msg) {
        for (ScheduleObserver o : observers) o.onConflict(attempted, conflicting, msg);
    }

    /**
     * Adds the task if no conflict. Throws TaskConflictException when conflict arises.
     */
    public void addTask(Task t) throws TaskConflictException {
        lock.writeLock().lock();
        try {
            String key = t.getDescription().toLowerCase();
            if (tasks.containsKey(key)) {
                throw new TaskConflictException("Task with same description already exists.", tasks.get(key));
            }

            // conflict detection: overlap detection
            for (Task existing : tasks.values()) {
                if (isOverlap(t.getStart(), t.getEnd(), existing.getStart(), existing.getEnd())) {
                    notifyConflict(t, existing, "Overlapping time period.");
                    throw new TaskConflictException("Task conflicts with existing task: " + existing.getDescription(), existing);
                }
            }

            tasks.put(key, t);
            logger.info("Task added: " + t.getDescription());
            notifyAdded(t);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeTask(String description) throws TaskNotFoundException {
        lock.writeLock().lock();
        try {
            String key = description.toLowerCase();
            Task removed = tasks.remove(key);
            if (removed == null) throw new TaskNotFoundException("Task not found: " + description);
            logger.info("Task removed: " + removed.getDescription());
            notifyRemoved(removed);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Task> viewAllTasks() {
        lock.readLock().lock();
        try {
            List<Task> list = new ArrayList<>(tasks.values());
            Collections.sort(list);
            return list;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Task> viewTasksByPriority(TaskPriority priority) {
        lock.readLock().lock();
        try {
            List<Task> out = new ArrayList<>();
            for (Task t : tasks.values()) {
                if (t.getPriority() == priority) out.add(t);
            }
            Collections.sort(out);
            return out;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void editTask(String description, LocalTime newStart, LocalTime newEnd, TaskPriority newPriority)
            throws TaskNotFoundException, TaskConflictException {
        lock.writeLock().lock();
        try {
            String key = description.toLowerCase();
            Task existing = tasks.get(key);
            if (existing == null) throw new TaskNotFoundException("Task not found: " + description);

            // create a temporal task to check conflict (excluding the current one)
            for (Task other : tasks.values()) {
                if (other.equals(existing)) continue;
                if (isOverlap(newStart, newEnd, other.getStart(), other.getEnd())) {
                    notifyConflict(existing, other, "Update causes overlap.");
                    throw new TaskConflictException("Updated time overlaps with " + other.getDescription(), other);
                }
            }

            existing.setStart(newStart);
            existing.setEnd(newEnd);
            existing.setPriority(newPriority);
            logger.info("Task updated: " + existing.getDescription());
            notifyUpdated(existing);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void markCompleted(String description) throws TaskNotFoundException {
        lock.writeLock().lock();
        try {
            String key = description.toLowerCase();
            Task t = tasks.get(key);
            if (t == null) throw new TaskNotFoundException("Task not found: " + description);
            t.markCompleted();
            logger.info("Task marked completed: " + t.getDescription());
            notifyUpdated(t);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean isOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        // overlap if s1 < e2 && s2 < e1
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    public Optional<Task> findByDescription(String description) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(tasks.get(description.toLowerCase()));
        } finally {
            lock.readLock().unlock();
        }
    }
}
