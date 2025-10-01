package com.astronaut.scheduler.observer;

import com.astronaut.scheduler.model.Task;

public interface TaskObserver {
    void onConflict(Task existingTask, Task newTask);
    void onTaskAdded(Task task);
    void onTaskRemoved(Task task);
}
