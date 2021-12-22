package com.projects.bugtracker.model.update;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {

    @NotNull
    private User user;
    private Bug newBug;
    private Group newGroup;
    private Project newProject;
    private String[] newSocialLink;
}
