package com.projects.bugtracker.model.update;

import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {

    @NotNull
    User user;
    @NotNull
    UserAccount userAccount;
}
