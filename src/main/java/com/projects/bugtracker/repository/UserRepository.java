package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserById(UUID id);

    void deleteUserById(UUID id);

    @Query("SELECT user FROM User user WHERE " +
            "user.firstName LIKE %?1% OR " +
            "user.lastName LIKE %?2% OR " +
            "user.email LIKE %?3%")
    List<User> searchUser(String firstName, String lastName, String email);

}
