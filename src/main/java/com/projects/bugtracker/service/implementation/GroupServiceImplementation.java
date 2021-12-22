package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.repository.GroupRepository;
import com.projects.bugtracker.service.BugService;
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
public class GroupServiceImplementation implements GroupService {

    private final GroupRepository groupRepository;
    private final ProjectService projectService;
    private final BugService bugService;

    @Override
    public Optional<Group> get(UUID id) {
        log.info("Group: FETCHING ID {}", id);
        return groupRepository.findGroupById(id);
    }

    @Override
    public Optional<Group> create(Group group, UUID projectId) {
        Optional<Project> groupProject = projectService.get(projectId);
        if(groupProject.isEmpty()){
            return Optional.empty();
        }
        log.info("Group: CREATE GROUP {}", group.getName());
        group.setDateCreated(now());
        group.setProject(groupProject.get());
        projectService.update(groupProject.get(), null, group);
        return Optional.of(groupRepository.save(group));
    }

    @Override
    public Optional<Group> update(
            Group group,
            User newContributor,
            Bug newBug
    ) {
        Optional<Group> originalGroup = groupRepository.findGroupById(group.getId());
        if(originalGroup.isEmpty()) {
            return Optional.empty();
        }
        Group updatedGroup = originalGroup.get();
        updatedGroup.setName(group.getName());
        updatedGroup.setBriefDescription(group.getBriefDescription());
        updatedGroup.setDateResolved(group.getDateResolved());
        updatedGroup.setBugsStatus(group.getBugsStatus());
        updatedGroup.setPriorityAverage(group.getPriorityAverage());
        Optional.ofNullable(newContributor).ifPresent(
                contributor -> updatedGroup
                        .getContributors()
                        .add(contributor)
        );
        Optional.ofNullable(newBug).ifPresent(
                bug -> updatedGroup
                        .getBugs()
                        .add(bug)
        );

        log.info("Group: UPDATED GROUP {}", group.getName());
        return Optional.of(groupRepository.save(updatedGroup));
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<Group> originalGroup = groupRepository.findGroupById(id);
        if(originalGroup.isEmpty()) {
            return false;
        }
        log.info("Group: DELETE ID {}", id);
        Set<Bug> bugs = originalGroup.get().getBugs();
        for(Bug bug: bugs) {
            bugService.delete(bug.getId());
        }
        groupRepository.deleteGroupById(id);
        return true;
    }

    @Override
    public List<Group> searchGroup(String name) {
        return groupRepository.searchGroup(name);
    }
}
