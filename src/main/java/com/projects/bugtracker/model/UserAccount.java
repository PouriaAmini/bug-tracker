package com.projects.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(
            name = "uuid2",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @JsonIgnore
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "userAccount",
            optional = false
    )
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRole position;

    private String email;

    private String password;

    private Boolean locked;

    private Boolean enabled;
}
