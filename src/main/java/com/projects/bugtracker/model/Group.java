package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    private Long id;
    private Long projectId;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;
    private Priority priorityAverage;
    private Status bugsStatus;
    private List<User> contributors;
    private List<Bug> bugs;
    private String briefDescription;
}
