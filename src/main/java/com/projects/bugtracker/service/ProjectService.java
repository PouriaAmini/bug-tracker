package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    Optional<Project> get(UUID id);
    Project create(Project project);
    Boolean delete(UUID id);
    List<Project> searchProject(String name);
    void updateEnum(
            Project project,
            Status newStatus,
            Priority newPriority
    );
    Optional<Project> update(
            Project project,
            User newContributor,
            Group newGroup
    );
}
