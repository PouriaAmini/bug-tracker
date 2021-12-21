package com.projects.bugtracker.service;

import com.projects.bugtracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID id);
    User create(User user);
    User update(User user);
    Boolean delete(User user);
    List<User> searchUser(String containing);
}
