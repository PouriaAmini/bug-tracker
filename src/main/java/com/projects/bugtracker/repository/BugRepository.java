package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface BugRepository extends JpaRepository<Bug, UUID> {

    Optional<Bug> findBugById(UUID id);

    void deleteBugById(UUID id);

    @Query(value = "SELECT name FROM trackerdb.bug WHERE " +
            "name LIKE '%:name%'", nativeQuery = true)
    List<Bug> searchBug(@Param("name") String name);
}
