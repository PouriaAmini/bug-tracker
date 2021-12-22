package com.projects.bugtracker.model.update;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugUpdate {

    @NotNull
    Bug bug;
    String[] newTriedSolution;
    User newAssignedTo;
}
