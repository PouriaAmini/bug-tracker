package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.repository.BugRepository;
import com.projects.bugtracker.service.BugService;
import com.projects.bugtracker.service.GroupService;
import com.projects.bugtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BugServiceImplementation implements BugService {

    private final BugRepository bugRepository;
    private final UserService userService;
    private final GroupService groupService;

    @Override
    public Optional<Bug> get(UUID id) {
        log.info("Bug: FETCHING ID {}", id);
        return bugRepository.findBugById(id);
    }

    @Override
    public Optional<Bug> create(Bug bug, UUID groupId, UUID creatorId) {
        Optional<Group> bugGroup = groupService.get(groupId);
        Optional<User> creator = userService.getById(creatorId);
        if(bugGroup.isEmpty() || creator.isEmpty()){
            return Optional.empty();
        }

        log.info("Bug: CREATE BUG {}", bug.getName());
        bug.setDateCreated(now());
        bug.setGroup(bugGroup.get());
        bug.setCreator(creator.get());
        Optional<Bug> savedBug = Optional.of(bugRepository.save(bug));
        groupService.update(
                bugGroup.get(),
                null,
                savedBug.get()
        );
        groupService.updateEnum(
                bugGroup.get(),
                savedBug.get().getStatus(),
                savedBug.get().getPriority()
        );
        return savedBug;
    }

    @Override
    public Optional<Bug> update(
            Bug bug,
            String[] newTriedSolution,
            User newAssignedTo
    ) {
        Optional<Bug> originalBug = bugRepository.findBugById(bug.getId());
        if(originalBug.isEmpty()) {
            return Optional.empty();
        }

        log.info("Bug: UPDATED BUG {}", bug.getName());
        Optional<Group> bugGroup = groupService.get(originalBug.get().getGroup().getId());
        Bug updatedBug = originalBug.get();
        updatedBug.setName(bug.getName());
        updatedBug.setIsAssigned(bug.getIsAssigned());
        updatedBug.setBriefDescription(bug.getBriefDescription());
        updatedBug.setFullDescription(bug.getFullDescription());
        updatedBug.setDateResolved(bug.getDateResolved());
        updatedBug.setStatus(bug.getStatus());
        updatedBug.setPriority(bug.getPriority());
        Optional.ofNullable(newTriedSolution).ifPresent(
                triedSolution -> updatedBug
                        .getTriedSolutions()
                        .put(newTriedSolution[0], newTriedSolution[1])
        );
        Optional.ofNullable(newAssignedTo).ifPresent(
                assignedTo -> updatedBug
                        .getAssignedTo()
                        .add(assignedTo)
        );
        groupService.updateEnum(
                bugGroup.get(),
                originalBug.get().getStatus(),
                originalBug.get().getPriority()
        );
        return Optional.of(bugRepository.save(updatedBug));
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<Bug> originalBug = bugRepository.findBugById(id);
        if(originalBug.isEmpty()) {
            return false;
        }

        log.info("Bug: DELETE ID {}", id);
        Optional<Group> bugGroup = groupService.get(originalBug.get().getGroup().getId());
        groupService.updateEnum(
                bugGroup.get(),
                originalBug.get().getStatus(),
                originalBug.get().getPriority()
        );
        bugRepository.deleteBugById(id);
        return true;
    }

    @Override
    public List<Bug> searchBug(String name) {
        return bugRepository.searchBug(name);
    }
}
