package com.projects.bugtracker.model.enumeration;

public enum Priority {

    HIGH(3),
    MEDIUM(2),
    LOW(1),
    UNKNOWN(0);

    private final Integer priority;

    Priority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}
