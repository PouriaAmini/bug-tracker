package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Project;
import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.update.ProjectUpdate;
import com.projects.bugtracker.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/find/{id}")
    ResponseEntity<Response> getProject(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        Optional<Project> project = projectService.get(id);

        if(project.isPresent()) {
            status = OK;
            statusCode = OK.value();
        } else {
            status = NOT_FOUND;
            statusCode = NOT_FOUND.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("PROJECT", project))
                        .message("PROJECT RETRIEVED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @GetMapping("/search/{name}")
    ResponseEntity<Response> searchProject(@PathVariable String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Projects", projectService.searchProject(name)))
                        .message("PROJECTS RETRIEVED WITH PATTERN: " + name)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/new")
    ResponseEntity<Response> createProject(@RequestBody Project project) {

        Project storedProject = projectService.create(project);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Project", storedProject))
                        .message("PROJECT CREATED WITH ID: " + storedProject.getId())
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateProject(@RequestBody @Valid ProjectUpdate projectUpdate) {

        Optional<Project> sameProject = projectService.update(
                projectUpdate.getProject(),
                projectUpdate.getNewContributor(),
                projectUpdate.getNewGroup()
        );

        int statusCode;
        HttpStatus status;
        String name;

        if(sameProject.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            name = "BAD REQUEST";
        }
        else {
            status = OK;
            statusCode = OK.value();
            name = sameProject.get().getName();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Project", sameProject.orElse(null)))
                        .message("PROJECT UPDATED WITH NAME: " + name)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteProject(@PathVariable UUID id) {

        int statusCode = NOT_FOUND.value();
        HttpStatus status = NOT_FOUND;
        String deleteMessage = " NOT ";

        boolean isDeleted = projectService.delete(id);

        if(isDeleted) {
            status = OK;
            statusCode = OK.value();
            deleteMessage = " ";
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Project", "DELETED: " + isDeleted))
                        .message("PROJECT" + deleteMessage + "DELETED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }
}













