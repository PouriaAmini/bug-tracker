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
public class Group {

    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )
    private Long id;
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
            cascade = CascadeType.ALL
    )
    private List<Bug> bugs = new ArrayList<>();

    @ManyToMany(mappedBy = "groups")
    private List<User> contributors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

}
