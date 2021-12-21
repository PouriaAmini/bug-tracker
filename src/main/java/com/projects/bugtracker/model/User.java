package com.projects.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
            optional = false
    )
    private UserAccount userAccount = new UserAccount();

    private String firstName;

    private String lastName;

    private String email;

    @ManyToMany
    private Set<Bug> assignedBugs = new HashSet<>();

    @ManyToMany
    private Set<Group> groups = new HashSet<>();

    @ManyToMany
    private Set<Project> projects = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserRole position;

    private String organization;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="social_links")
    @CollectionTable(name="user_social_links", joinColumns=@JoinColumn(name="user_id"))
    private Map<String, String> socialLinks = new HashMap<>();
}

