package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    void deleteUserById(Long id);

    @Modifying
    @Query("UPDATE User user SET user.firstName = ?1 WHERE user.id = ?2")
    void updateUserFirstName(String firstName, Long id);

    @Modifying
    @Query("UPDATE User user SET user.lastName = ?1 WHERE user.id = ?2")
    void updateUserLastName(String lastName, Long id);

    @Modifying
    @Query("UPDATE User user SET user.email = ?1 WHERE user.id = ?2")
    void updateUserEmail(String Email, Long id);

    @Query("SELECT user FROM User user WHERE " +
            "user.firstName LIKE %?1% OR " +
            "user.lastName LIKE %?2% OR " +
            "user.email LIKE %?3%")
    List<User> searchUser(String firstName, String lastName, String email);

}
