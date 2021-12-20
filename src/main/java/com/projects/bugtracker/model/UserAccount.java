package com.projects.bugtracker.model;

import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserAccount {

    @Id
    @SequenceGenerator(
            name = "user_account_sequence",
            sequenceName = "user_account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_account_sequence"
    )
    private Long id;

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
