package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.repository.ProjectRepository;
import com.projects.bugtracker.service.GroupService;
import com.projects.bugtracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GroupService groupService;

    @Override
    public Optional<Project> get(UUID id) {
        log.info("Project: FETCHING ID {}", id);
        return projectRepository.findProjectById(id);
    }

    @Override
    public Project create(Project project) {
        project.setDateCreated(now());
        log.info("Project: CREATE PROJECT {}", project.getName());
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> update(
            Project project,
            User newContributor,
            Group newGroup
    ) {
        Optional<Project> originalProject = projectRepository.findProjectById(project.getId());
        if(originalProject.isEmpty()) {
            return Optional.empty();
        }
        Project updatedProject = originalProject.get();
        updatedProject.setName(project.getName());
        updatedProject.setBriefDescription(project.getBriefDescription());
        updatedProject.setDateResolved(project.getDateResolved());
        updatedProject.setGroupsStatus(project.getGroupsStatus());
        updatedProject.setPriorityAverage(project.getPriorityAverage());
        Optional.ofNullable(newContributor).ifPresent(
                contributor -> updatedProject
                        .getContributors()
                        .add(contributor)
        );
        Optional.ofNullable(newGroup).ifPresent(
                group -> updatedProject
                        .getGroups()
                        .add(group)
        );

        log.info("Project: UPDATED PROJECT {}", project.getName());
        return Optional.of(projectRepository.save(updatedProject));
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<Project> originalProject = projectRepository.findProjectById(id);
        if(originalProject.isEmpty()) {
            return false;
        }
        log.info("Project: DELETE ID {}", id);
        Set<Group> groups = originalProject.get().getGroups();
        for(Group group: groups) {
            groupService.delete(group.getId());
        }
        projectRepository.deleteProjectById(id);
        return true;
    }

    @Override
    public List<Project> searchProject(String name) {
        return projectRepository.searchProject(name);
    }
}
