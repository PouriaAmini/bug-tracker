package com.projects.bugtracker.service;

import com.projects.bugtracker.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID id);
    Optional<User> create(User user);
    Optional<User> update(User user);
    Boolean delete(UUID id);
    List<User> searchUser(String containing);
}
