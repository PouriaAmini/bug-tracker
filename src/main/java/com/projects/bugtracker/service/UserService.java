package com.projects.bugtracker.service;

import com.projects.bugtracker.model.User;

import java.util.List;

public interface UserService {
    User get(Long id);
    User create(User user);
    User update(User user);
    Boolean delete(User user);
    List<User> searchUser(String firstName, String lastName, String email);
}
