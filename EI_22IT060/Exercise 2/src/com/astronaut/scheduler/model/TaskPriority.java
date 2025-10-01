package com.astronaut.scheduler.model;

public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH;

    public static TaskPriority fromString(String s) {
        if (s == null) return MEDIUM;
        switch (s.trim().toLowerCase()) {
            case "low": return LOW;
            case "medium": return MEDIUM;
            case "high": return HIGH;
            default: return MEDIUM;
        }
    }
}
