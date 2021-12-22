package com.projects.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.bugtracker.model.enumeration.Priority;
import com.projects.bugtracker.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

import static com.projects.bugtracker.model.enumeration.Priority.UNKNOWN;

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
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;
    private String briefDescription;

    @Enumerated(EnumType.STRING)
    private Priority priorityAverage = UNKNOWN;

    @Enumerated(EnumType.STRING)
    private Status bugsStatus = Status.UNKNOWN;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")

    private List<User> contributors = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "group",
            orphanRemoval = true
    )
    private List<Bug> bugs = new ArrayList<>();
}
