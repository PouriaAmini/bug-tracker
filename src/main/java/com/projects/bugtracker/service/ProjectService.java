package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project get(UUID id);
    Project create(Project project);
    Project update(Project project);
    Boolean delete(Project project);
    List<Project> searchProject(String name);
}
