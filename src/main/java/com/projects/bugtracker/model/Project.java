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
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

import static com.projects.bugtracker.model.enumeration.Priority.*;

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

    @Enumerated(EnumType.STRING)
    private Priority priorityAverage = UNKNOWN;

    @Enumerated(EnumType.STRING)
    private Status groupsStatus = Status.UNKNOWN;

    @ManyToMany(mappedBy = "projects")
    private List<User> contributors = new ArrayList<>();

    @OneToMany(
            mappedBy = "project",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Group> groups = new ArrayList<>();
}
