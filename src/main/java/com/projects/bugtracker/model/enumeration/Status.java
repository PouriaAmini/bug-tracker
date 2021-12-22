package com.projects.bugtracker.model.enumeration;

public enum Status {

    RESOLVED(3),
    PENDING(2),
    UNASSIGNED(1),
    UNKNOWN(0);

    private final Integer status;

    Status(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
