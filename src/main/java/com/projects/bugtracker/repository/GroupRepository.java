package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findGroupById(Long id);

    void deleteGroupById(Long id);

    @Modifying
    @Query("UPDATE Group group SET group.name = ?1 WHERE group.id = ?2")
    void updateGroupName(String name, Long id);

    @Modifying
    @Query("UPDATE Bug bug SET bug.dateResolved = ?1 WHERE bug.id = ?2")
    void updateBugDateResolved(LocalDateTime dateResolved, Long id);

    @Modifying
    @Query("UPDATE Bug bug SET bug.isAssigned = ?1 WHERE bug.id = ?2")
    void updateBugIsAssigned(Boolean isAssigned, Long id);

    @Modifying
    @Query("UPDATE Bug bug SET bug.briefDescription = ?1 WHERE bug.id = ?2")
    void updateBugBriefDescription(String briefDescription, Long id);

    @Query("SELECT bug FROM Bug bug WHERE " +
            "bug.name LIKE %?1%")
    List<Group> searchBug(String name);
}
