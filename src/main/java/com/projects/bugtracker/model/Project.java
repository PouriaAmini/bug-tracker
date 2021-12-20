package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(
            name = "uuid2",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;

    private String briefDescription;

    @Enumerated(EnumType.STRING)
    private Priority priorityAverage;

    @Enumerated(EnumType.STRING)
    private Status groupsStatus;

    @ManyToMany(mappedBy = "projects")
    private Set<User> contributors = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            orphanRemoval = true
    )
    private Set<Group> groups = new HashSet<>();
}
