package com.projects.bugtracker.model.enumeration;

public enum UserRole {

    USER("USER"),
    MANAGER("MANAGER");

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
