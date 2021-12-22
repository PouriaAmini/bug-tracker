package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
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
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findProjectById(UUID id);

    void deleteProjectById(UUID id);

    @Query(value = "SELECT name FROM trackerdb.project WHERE " +
            "name LIKE '%:name%'", nativeQuery = true
    )
    List<Project> searchProject(@Param("name") String name);
}
