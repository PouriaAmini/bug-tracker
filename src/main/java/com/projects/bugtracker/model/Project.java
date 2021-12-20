package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long id;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;

    private String briefDescription;

    @Enumerated(EnumType.STRING)
    private Priority priorityAverage;

    @Enumerated(EnumType.STRING)
    private Status groupsStatus;

    @ManyToMany(mappedBy = "projects")
    private List<User> contributors = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "project"
    )
    private List<Group> groups = new ArrayList<>();
}
