package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Project;

import java.util.List;

public interface ProjectService {
    Project get(Long id);
    Project create(Project project);
    Project update(Project project);
    Boolean delete(Project project);
    List<Project> searchProject(String name);
}
