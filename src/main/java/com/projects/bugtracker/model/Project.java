package com.projects.bugtracker.model;

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
import javax.validation.constraints.Size;
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
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 40)
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;

    @Size(max = 150)
    private String briefDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priorityAverage;

    @NotNull
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
