package com.projects.bugtracker.model.enumeration;

public enum Status {

    RESOLVED("RESOLVED"),
    PENDING("PENDING"),
    UNASSIGNED("UNASSIGNED");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
