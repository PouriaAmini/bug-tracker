package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.*;
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

    @Override
    public Optional<User> getById(UUID id) {
        log.info("User: FETCHING ID {}", id);
        return userRepository.findUserById(id);
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
