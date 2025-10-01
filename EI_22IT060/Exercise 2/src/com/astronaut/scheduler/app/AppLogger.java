package com.astronaut.scheduler.app;

import java.io.IOException;
import java.util.logging.*;

public final class AppLogger {
    private static volatile AppLogger instance;
    private final Logger logger;

    private AppLogger() {
        logger = Logger.getLogger("AstronautScheduleLogger");
        logger.setUseParentHandlers(false); // avoid duplicate console logs

        // Console handler
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());
        logger.addHandler(ch);

        // File handler (rotating)
        try {
            Handler fh = new FileHandler("astronaut_schedule.log", 1024 * 1024, 3, true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            // If file handler fails, at least continue with console logging
            logger.log(Level.WARNING, "File logging disabled: " + e.getMessage());
        }

        logger.setLevel(Level.INFO);
    }

    public static AppLogger getInstance() {
        if (instance == null) {
            synchronized (AppLogger.class) {
                if (instance == null) instance = new AppLogger();
            }
        }
        return instance;
    }

    public void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    public void warning(String msg) {
        logger.log(Level.WARNING, msg);
    }

    public void error(String msg, Throwable t) {
        logger.log(Level.SEVERE, msg, t);
    }
}
