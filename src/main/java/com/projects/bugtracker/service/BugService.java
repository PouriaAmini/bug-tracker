package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BugService {
    Optional<Bug> get(UUID id);
    Optional<Bug> create(Bug bug, UUID groupId, UUID creatorId);
    Optional<Bug> update(
            Bug bug,
            String[] newTriedSolution,
            User newAssignedTo
    );
    Boolean delete(UUID id);
    List<Bug> searchBug(String name);
}
