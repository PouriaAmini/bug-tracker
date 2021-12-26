package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findProjectById(UUID id);

    @Query("SELECT project FROM Project project")
    List<Project> findAll();

    void deleteProjectById(UUID id);

    @Query("SELECT project FROM Project project WHERE " +
            "project.name LIKE %?1%")
    List<Project> searchProject(String name);
}
