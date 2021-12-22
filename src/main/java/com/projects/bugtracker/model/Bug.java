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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bug {

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
    private Boolean isAssigned;
    private String briefDescription;
    private String fullDescription;   //TODO Change the type to something that can hold a file;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    @MapKeyColumn(name="number")
    @Column(name="tried_solutions")
    @CollectionTable(name="bug_tried_solutions", joinColumns=@JoinColumn(name="bug_id"))
    private Map<String, String> triedSolutions = new HashMap<>();

    @ManyToMany(mappedBy = "assignedBugs")
    private List<User> assignedTo = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;
}
