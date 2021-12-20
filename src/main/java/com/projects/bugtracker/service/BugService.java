package com.projects.bugtracker.service;

import com.projects.bugtracker.model.Bug;

import java.util.List;

public interface BugService {
    Bug get(Long id);
    Bug create(Bug bug);
    Bug update(Bug bug);
    Boolean delete(Bug bug);
    List<Bug> searchBug(String name);
}
