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
public class Group {

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
    private Status bugsStatus;

    @OneToMany(
            mappedBy = "group",
            orphanRemoval = true
    )
    private Set<Bug> bugs = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<User> contributors = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

}
