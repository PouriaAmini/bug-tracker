package com.projects.bugtracker.service;

import com.projects.bugtracker.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {
    Optional<UserAccount> create(UserAccount userAccount);
    Optional<UserAccount> get(String email);
    List<UserAccount> getUserAccounts();
}
