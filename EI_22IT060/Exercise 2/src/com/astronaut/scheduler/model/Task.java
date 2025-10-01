package com.astronaut.scheduler.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Task implements Comparable<Task> {
    private final String description;         // must be unique for simplicity
    private LocalTime start;
    private LocalTime end;
    private TaskPriority priority;
    private boolean completed = false;

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

    public Task(String description, LocalTime start, LocalTime end, TaskPriority priority) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be empty.");
        if (start == null || end == null)
            throw new IllegalArgumentException("Start and end times must be provided.");
        if (!start.isBefore(end))
            throw new IllegalArgumentException("Start time must be before end time.");

        this.description = description.trim();
        this.start = start;
        this.end = end;
        this.priority = priority == null ? TaskPriority.MEDIUM : priority;
    }

    public String getDescription() { return description; }
    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }
    public TaskPriority getPriority() { return priority; }
    public boolean isCompleted() { return completed; }

    public void setStart(LocalTime start) {
        if (start == null) throw new IllegalArgumentException("Start cannot be null.");
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        if (end == null) throw new IllegalArgumentException("End cannot be null.");
        this.end = end;
    }

    public void setPriority(TaskPriority priority) {
        if (priority != null) this.priority = priority;
    }

    public void markCompleted() { this.completed = true; }

    @Override
    public int compareTo(Task other) {
        int cmp = this.start.compareTo(other.start);
        if (cmp != 0) return cmp;
        return this.end.compareTo(other.end);
    }

    @Override
    public String toString() {
        String base = String.format("%s - %s: %s [%s]",
                start.format(fmt), end.format(fmt), description, priority);
        if (completed) base += " (Completed)";
        return base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task t = (Task) o;
        return description.equalsIgnoreCase(t.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description.toLowerCase());
    }
}
