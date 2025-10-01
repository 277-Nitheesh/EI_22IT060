package com.astronaut.scheduler.exceptions;

import com.astronaut.scheduler.model.Task;

public class TaskConflictException extends Exception {
    private final Task conflictingTask;
    public TaskConflictException(String message, Task conflictingTask) {
        super(message);
        this.conflictingTask = conflictingTask;
    }
    public Task getConflictingTask() { return conflictingTask; }
}
