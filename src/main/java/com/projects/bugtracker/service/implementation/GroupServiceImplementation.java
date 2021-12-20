package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.repository.GroupRepository;
import com.projects.bugtracker.service.GroupService;
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
public class GroupServiceImplementation implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group get(UUID id) {
        return null;
    }

    @Override
    public Group create(Group group) {
        return null;
    }

    @Override
    public Group update(Group group) {
        return null;
    }

    @Override
    public Boolean delete(Group group) {
        return null;
    }

    @Override
    public List<Group> searchGroup(String name) {
        return null;
    }
}
