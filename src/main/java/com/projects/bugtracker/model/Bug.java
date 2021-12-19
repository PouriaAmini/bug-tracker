package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bug {

    private Long id;
    private Long groupId; // point it to the assigned group
    private Long projectId;  // point it to the assigned group
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;
    private Boolean isAssigned;
    private List<User> assignedTo;
    private Priority priority;
    private Status status;
    private User creator;
    private Map<Integer, String> triedSolutions;
    private String briefDescription;
    private String fullDescription;   // Change the type to something that can hold a file;
}
