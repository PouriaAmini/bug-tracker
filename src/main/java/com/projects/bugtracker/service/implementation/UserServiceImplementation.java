package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.User;
import com.projects.bugtracker.repository.UserRepository;
import com.projects.bugtracker.service.UserService;
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
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public User get(UUID id) {
        return null;
    }

    @Override
    public User create(User user) {
        log.info("user: CREATE ID {}", user.getFirstName());
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Boolean delete(User user) {
        return null;
    }

    @Override
    public List<User> searchUser(String firstName, String lastName, String email) {
        return null;
    }
}
