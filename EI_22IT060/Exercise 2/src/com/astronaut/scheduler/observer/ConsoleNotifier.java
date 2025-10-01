package com.astronaut.scheduler.observer;

import com.astronaut.scheduler.app.AppLogger;
import com.astronaut.scheduler.model.Task;

public class ConsoleNotifier implements ScheduleObserver {
    private final AppLogger logger = AppLogger.getInstance();

    @Override
    public void onTaskAdded(Task task) {
        String msg = "NOTIFY: Task added -> " + task.toString();
        System.out.println(msg);
        logger.info(msg);
    }

    @Override
    public void onTaskRemoved(Task task) {
        String msg = "NOTIFY: Task removed -> " + task.toString();
        System.out.println(msg);
        logger.info(msg);
    }

    @Override
    public void onTaskUpdated(Task task) {
        String msg = "NOTIFY: Task updated -> " + task.toString();
        System.out.println(msg);
        logger.info(msg);
    }

    @Override
    public void onConflict(Task attempted, Task conflicting, String message) {
        String msg = "ALERT: Conflict when adding '" + attempted.getDescription() + "'. Conflicts with '" +
                conflicting.getDescription() + "'. " + message;
        System.out.println(msg);
        logger.warning(msg);
    }
}
