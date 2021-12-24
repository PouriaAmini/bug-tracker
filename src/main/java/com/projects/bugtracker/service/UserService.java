package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID id);
    Optional<User> create(User user);
    Boolean assign(
            List<UUID> userIds,
            UUID managerId,
            UUID bugId
    );
    Optional<User> update(User user,
                          Bug newBug,
                          Group newGroup,
                          Project newProject,
                         String[] newSocialLink);
    Boolean delete(UUID id);
    List<User> searchUser(String containing);
}
