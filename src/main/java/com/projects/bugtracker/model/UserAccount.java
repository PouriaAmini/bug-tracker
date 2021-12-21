package com.projects.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.bugtracker.model.enumeration.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAccount {

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
            mappedBy = "userAccount",
            optional = false
    )
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole position;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Boolean locked;

    @JsonIgnore
    private Boolean enabled;
}
