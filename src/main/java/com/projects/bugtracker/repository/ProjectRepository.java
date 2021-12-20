package com.projects.bugtracker.repository;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findProjectById(Long id);

    void deleteProjectById(Long id);

    @Query("SELECT project FROM Project project WHERE " +
            "project.name LIKE %?1%")
    List<Group> searchProject(String name);
}
