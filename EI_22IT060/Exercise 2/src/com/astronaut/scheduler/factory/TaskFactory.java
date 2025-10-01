package com.astronaut.scheduler.factory;

import com.astronaut.scheduler.exceptions.InvalidTimeFormatException;
import com.astronaut.scheduler.model.Task;
import com.astronaut.scheduler.model.TaskPriority;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class TaskFactory {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TaskFactory() {}

    /**
     * Creates a Task object after parsing and validating times.
     * Throws InvalidTimeFormatException on parse errors or invalid ranges.
     */
    public static Task createTask(String description, String startStr, String endStr, String priorityStr)
            throws InvalidTimeFormatException {
        try {
            LocalTime s = LocalTime.parse(startStr.trim(), FORMATTER);
            LocalTime e = LocalTime.parse(endStr.trim(), FORMATTER);

            if (!s.isBefore(e)) {
                throw new InvalidTimeFormatException("Start time must be before end time.");
            }

            TaskPriority p = TaskPriority.fromString(priorityStr);
            return new Task(description.trim(), s, e, p);
        } catch (DateTimeParseException ex) {
            throw new InvalidTimeFormatException("Invalid time format. Use HH:mm (24-hour).");
        } catch (NullPointerException ex) {
            throw new InvalidTimeFormatException("Start/end times cannot be null or empty.");
        }
    }
}
