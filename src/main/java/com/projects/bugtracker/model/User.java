package com.projects.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(
            name = "uuid2",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false,
            orphanRemoval = true
    )
    private UserAccount userAccount = new UserAccount();

    @Size(min = 3, message = "First name should be at least 3 letters!")
    private String firstName;

    @Size(min = 3, message = "Last name should be at least 3 letters!")
    private String lastName;

    @Email(message = "Wrong format of email!")
    private String email;

    @ManyToMany
    private List<Bug> assignedBugs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "creator",
            orphanRemoval = true
    )
    private List<Bug> createdBugs = new ArrayList<>();

    @ManyToMany
    private List<Group> groups = new ArrayList<>();

    @ManyToMany
    private List<Project> projects = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole position;

    private String organization;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="social_links")
    @CollectionTable(name="user_social_links", joinColumns=@JoinColumn(name="user_id"))
    private Map<String, String> socialLinks = new HashMap<>();
}

