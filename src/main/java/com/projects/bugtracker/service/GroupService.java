package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Group;

import java.util.List;

public interface GroupService {
    Group get(Long id);
    Group create(Group group);
    Group update(Group group);
    Boolean delete(Group group);
    List<Group> searchGroup(String name);
}
