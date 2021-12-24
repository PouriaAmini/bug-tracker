package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.*;
import com.projects.bugtracker.repository.BugRepository;
import com.projects.bugtracker.repository.GroupRepository;
import com.projects.bugtracker.repository.ProjectRepository;
import com.projects.bugtracker.repository.UserRepository;
import com.projects.bugtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final BugRepository bugRepository;
    private final GroupRepository groupRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Optional<User> getById(UUID id) {
        log.info("User: FETCHING ID {}", id);
        return userRepository.findUserById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        log.info("User: FETCHING EMAIL {}", email);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> create(User user) {
        Optional<User> retrievedUserSameEmail = userRepository.findUserByEmail(user.getEmail());
        if(retrievedUserSameEmail.isPresent()) {
            return Optional.empty();
        }
        log.info("User: CREATE USER {}", user.getFirstName());
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Boolean assign(List<UUID> userIds, UUID managerId, UUID bugId) {
        Optional<User> manager = userRepository.findUserById(managerId);
        Optional<Bug> bug = bugRepository.findBugById(bugId);
        if(manager.isEmpty() || bug.isEmpty()){
            return false;
        }
        Optional<Group> group = groupRepository.findGroupById(bug.get().getGroup().getId());
        Optional<Project> project = projectRepository.findProjectById(group.get().getProject().getId());
        for(UUID id: userIds) {
            Optional<User> user = userRepository.findUserById(id);
            if(user.isEmpty()) {
                log.error("USER WITH ID {} WAS NOT FOUND", id);
            } else {
                List<Bug> alreadyAssigned = user.get().getAssignedBugs();
                alreadyAssigned.add(bug.get());
                user.get().setAssignedBugs(alreadyAssigned);
                userRepository.save(user.get());

                List<User> usersAssignedTo = bug.get().getAssignedTo();
                usersAssignedTo.add(user.get());
                bug.get().setAssignedTo(usersAssignedTo);
                bugRepository.save(bug.get());

                List<User> groupContributors = group.get().getContributors();
                groupContributors.add(user.get());
                group.get().setContributors(groupContributors);
                groupRepository.save(group.get());

                List<User> projectContributors = project.get().getContributors();
                projectContributors.add(user.get());
                project.get().setContributors(projectContributors);
                projectRepository.save(project.get());
            }
        }
        return true;
    }

    @Override
    public Optional<User> update(
            User user,
            Bug newBug,
            Group newGroup,
            Project newProject,
            String[] newSocialLink
    ) {
        Optional<User> originalUser = userRepository.findUserById(user.getId());
        Optional<User> similarUSer = userRepository.findUserByEmail(user.getEmail());
        if(originalUser.isEmpty()) {
            return Optional.empty();
        }
        else if(similarUSer.isPresent()) {
            if(!originalUser.get().getId().equals(similarUSer.get().getId())) {
                return Optional.empty();
            }
        }
        User updatedUser = originalUser.get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setOrganization(user.getOrganization());
        Optional.ofNullable(newBug).ifPresent(
                bug -> updatedUser
                        .getAssignedBugs()
                        .add(bug)
        );
        Optional.ofNullable(newGroup).ifPresent(
                group -> updatedUser
                        .getGroups()
                        .add(group)
        );
        Optional.ofNullable(newProject).ifPresent(
                project -> updatedUser
                        .getProjects()
                        .add(project)
        );
        Optional.ofNullable(newSocialLink).ifPresent(
                socialLink -> updatedUser
                        .getSocialLinks()
                        .put(newSocialLink[0], newSocialLink[1])
        );

        log.info("User: UPDATED USER {}", user.getFirstName());
        return Optional.of(userRepository.save(updatedUser));
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<User> originalUser = userRepository.findUserById(id);
        if(originalUser.isEmpty()) {
            return false;
        }
        log.info("User: DELETE ID {}", id);
        userRepository.deleteUserById(id);
        return true;
    }

    @Override
    public List<User> searchUser(String containing) {
        return userRepository.searchUser(containing);
    }
}
