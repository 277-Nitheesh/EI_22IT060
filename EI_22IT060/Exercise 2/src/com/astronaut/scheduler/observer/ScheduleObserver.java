package com.astronaut.scheduler.observer;

import com.astronaut.scheduler.model.Task;

public interface ScheduleObserver {
    void onTaskAdded(Task task);
    void onTaskRemoved(Task task);
    void onTaskUpdated(Task task);
    void onConflict(Task attempted, Task conflicting, String message);
}
