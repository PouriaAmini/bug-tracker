package com.projects.bugtracker.model.update;

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
public class ProjectUpdate {

    @NotNull
    Project project;
    User newContributor;
    Group newGroup;
}
