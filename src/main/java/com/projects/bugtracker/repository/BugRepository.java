package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface BugRepository extends JpaRepository<Bug, Long> {

    Optional<Bug> findBugById(Long id);

    void deleteBugById(Long id);

    @Query("SELECT bug FROM Bug bug WHERE " +
            "bug.name LIKE %?1%")
    List<Bug> searchBug(String name);
}
