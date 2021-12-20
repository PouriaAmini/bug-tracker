package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Bug;
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
public interface BugRepository extends JpaRepository<Bug, Long> {

    Optional<Bug> findBugById(Long id);

    void deleteBugById(Long id);

    @Modifying
    @Query("UPDATE Bug bug SET bug.name = ?1 WHERE bug.id = ?2")
    void updateBugName(String name, Long id);

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
    List<Bug> searchBug(String name);
}
