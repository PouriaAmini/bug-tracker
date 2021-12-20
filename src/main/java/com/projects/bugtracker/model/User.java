package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    private UserAccount userAccount;

    private String firstName;

    private String lastName;

    private String email;

    @ManyToMany
    private List<Bug> assignedBugs = new ArrayList<>();

    @ManyToMany
    private List<Group> groups = new ArrayList<>();

    @ManyToMany
    private List<Project> projects = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole position;

    private String organization;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="social_links")
    @CollectionTable(name="user_social_links", joinColumns=@JoinColumn(name="user_id"))
    private Map<String, String> socialLinks = new HashMap<>();
}

