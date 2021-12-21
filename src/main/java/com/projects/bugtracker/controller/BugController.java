package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.service.BugService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    private final BugService bugService;

    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @PostMapping
    Bug createBug(@RequestBody Bug bug) {
        return bugService.create(bug);
    }
}
