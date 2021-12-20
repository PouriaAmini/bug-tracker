package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group get(UUID id);
    Group create(Group group);
    Group update(Group group);
    Boolean delete(Group group);
    List<Group> searchGroup(String name);
}
