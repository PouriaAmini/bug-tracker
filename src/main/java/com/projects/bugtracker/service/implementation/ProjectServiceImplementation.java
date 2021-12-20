package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.repository.ProjectRepository;
import com.projects.bugtracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project get(UUID id) {
        return null;
    }

    @Override
    public Project create(Project project) {
        return null;
    }

    @Override
    public Project update(Project project) {
        return null;
    }

    @Override
    public Boolean delete(Project project) {
        return null;
    }

    @Override
    public List<Project> searchProject(String name) {
        return null;
    }
}
