package com.astronaut.scheduler;

import com.astronaut.scheduler.app.AppLogger;
import com.astronaut.scheduler.exceptions.InvalidTimeFormatException;
import com.astronaut.scheduler.exceptions.TaskConflictException;
import com.astronaut.scheduler.exceptions.TaskNotFoundException;
import com.astronaut.scheduler.factory.TaskFactory;
import com.astronaut.scheduler.model.Task;
import com.astronaut.scheduler.model.TaskPriority;
import com.astronaut.scheduler.observer.ConsoleNotifier;
import com.astronaut.scheduler.scheduler.ScheduleManager;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based entry point. Demonstrates required features:
 * Add/Remove/View tasks, validate overlaps, logging, observer notifications.
 *
 * NOTE: For simplicity we assume task description is unique.
 */
public class Main {
    private static final ScheduleManager schedule = ScheduleManager.getInstance();
    private static final AppLogger logger = AppLogger.getInstance();

    public static void main(String[] args) {
        // Attach a console notifier observer
        schedule.attachObserver(new ConsoleNotifier());

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        printHeader();
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> handleAdd(sc);
                case "2" -> handleRemove(sc);
                case "3" -> handleViewAll();
                case "4" -> handleEdit(sc);
                case "5" -> handleMarkCompleted(sc);
                case "6" -> handleViewByPriority(sc);
                case "7" -> {
                    System.out.println("Exiting...");
                    logger.info("Application shutting down by user.");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Enter 1..7");
            }
        }
        sc.close();
    }

    private static void printHeader() {
        System.out.println("========================================");
        System.out.println(" Astronaut Daily Schedule Organizer");
        System.out.println("========================================");
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. View All Tasks");
        System.out.println("4. Edit Task");
        System.out.println("5. Mark Task Completed");
        System.out.println("6. View Tasks by Priority");
        System.out.println("7. Exit");
        System.out.print("Choose (1-7): ");
    }

    private static void handleAdd(Scanner sc) {
        try {
            System.out.print("Description: ");
            String desc = sc.nextLine().trim();
            System.out.print("Start (HH:mm): ");
            String start = sc.nextLine().trim();
            System.out.print("End (HH:mm): ");
            String end = sc.nextLine().trim();
            System.out.print("Priority (low/medium/high) [default medium]: ");
            String pr = sc.nextLine().trim();
            if (pr.isBlank()) pr = "medium";

            Task t = TaskFactory.createTask(desc, start, end, pr);
            schedule.addTask(t);
            System.out.println("Task added successfully.");
        } catch (InvalidTimeFormatException e) {
            logger.warning("Add task failed: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } catch (TaskConflictException e) {
            logger.warning("Add task conflict: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding task", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleRemove(Scanner sc) {
        try {
            System.out.print("Enter description of task to remove: ");
            String desc = sc.nextLine().trim();
            schedule.removeTask(desc);
            System.out.println("Task removed successfully.");
        } catch (TaskNotFoundException e) {
            logger.warning("Remove failed: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while removing task", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleViewAll() {
        List<Task> tasks = schedule.viewAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        System.out.println("\nTasks (sorted by start time):");
        int idx = 1;
        for (Task t : tasks) {
            System.out.println(idx++ + ". " + t.toString());
        }
    }

    private static void handleEdit(Scanner sc) {
        try {
            System.out.print("Enter description of task to edit: ");
            String desc = sc.nextLine().trim();
            System.out.print("New Start (HH:mm): ");
            String s = sc.nextLine().trim();
            System.out.print("New End (HH:mm): ");
            String e = sc.nextLine().trim();
            System.out.print("New Priority (low/medium/high): ");
            String p = sc.nextLine().trim();
            if (p.isBlank()) p = "medium";

            Task temp = TaskFactory.createTask(desc, s, e, p);
            schedule.editTask(desc, temp.getStart(), temp.getEnd(), temp.getPriority());
            System.out.println("Task updated successfully.");
        } catch (TaskNotFoundException ex) {
            logger.warning("Edit failed: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        } catch (TaskConflictException ex) {
            logger.warning("Edit conflict: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        } catch (InvalidTimeFormatException ex) {
            logger.warning("Invalid time: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error while editing task", ex);
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void handleMarkCompleted(Scanner sc) {
        try {
            System.out.print("Enter description of completed task: ");
            String desc = sc.nextLine().trim();
            schedule.markCompleted(desc);
            System.out.println("Task marked completed.");
        } catch (TaskNotFoundException ex) {
            logger.warning("Mark completed failed: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error while marking completed", ex);
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void handleViewByPriority(Scanner sc) {
        System.out.print("Enter priority (low/medium/high): ");
        String p = sc.nextLine().trim();
        TaskPriority pr = TaskPriority.fromString(p);
        List<com.astronaut.scheduler.model.Task> tasks = schedule.viewTasksByPriority(pr);
        if (tasks.isEmpty()) {
            System.out.println("No tasks with priority " + pr);
            return;
        }
        System.out.println("\nTasks with priority " + pr + ":");
        int idx = 1;
        for (Task t : tasks) {
            System.out.println(idx++ + ". " + t.toString());
        }
    }
}
