package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.User;
import com.projects.bugtracker.repository.UserRepository;
import com.projects.bugtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public User create(User user) {
        Optional<User> retrievedUserSameEmail = userRepository.findUserByEmail(user.getEmail());
        if(retrievedUserSameEmail.isPresent()) {
            throw new EntityNotFoundException("EMAIL ALREADY EXISTS: " + user.getEmail());
        }
        log.info("user: CREATE ID {}", user.getFirstName());
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        Optional<User> originalUser = userRepository.findUserById(user.getId());
        System.out.println(originalUser.get().getId());
        if(originalUser.isEmpty()) {
            throw new EntityNotFoundException("NO USER FOUND WITH ID: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(User user) {
        Optional<User> originalUser = userRepository.findUserById(user.getId());
        if(originalUser.isEmpty()) {
            throw new EntityNotFoundException("NO USER FOUND WITH ID: " + user.getId());
        }
        userRepository.deleteUserById(user.getId());
        return true;
    }

    @Override
    public List<User> searchUser(String containing) {
        return userRepository.searchUser(containing);
    }
}
