package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupService {
    Optional<Group> get(UUID id);
    Optional<Group> create(Group group, UUID projectId);
    Boolean delete(UUID id);
    List<Group> searchGroup(String name);
    void updateEnum(
            Group group,
            Status newStatus,
            Priority newPriority
    );
    Optional<Group> update(
            Group group,
            User newContributor,
            Bug newBug
    );
}
