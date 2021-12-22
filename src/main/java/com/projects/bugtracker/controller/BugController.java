package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Bug;
import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.update.BugUpdate;
import com.projects.bugtracker.service.BugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    private final BugService bugService;

    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/find/{id}")
    ResponseEntity<Response> getBug(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        Optional<Bug> bug = bugService.get(id);

        if(bug.isPresent()) {
            status = OK;
            statusCode = OK.value();
        } else {
            status = NOT_FOUND;
            statusCode = NOT_FOUND.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("BUG", bug))
                        .message("BUG RETRIEVED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @GetMapping("/search/{name}")
    ResponseEntity<Response> searchBugs(@PathVariable String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Bugs", bugService.searchBug(name)))
                        .message("BUGS RETRIEVED WITH PATTERN: " + name)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/new")
    ResponseEntity<Response> createBug(
            @RequestParam(value = "group-id")UUID groupId,
            @RequestParam(value = "creator-id")UUID creatorId,
            @RequestBody Bug bug
    ) {

        int statusCode = NOT_FOUND.value();
        HttpStatus status = NOT_FOUND;
        Optional<Bug> createdGroup = bugService.create(bug, groupId, creatorId);

        if(createdGroup.isPresent()) {
            status = CREATED;
            statusCode = CREATED.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("BUG", bug))
                        .message("BUG CREATED WITH ID: " + bug.getId())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );

    }

    @PutMapping("/update")
    ResponseEntity<Response> updateBug(@RequestBody @Valid BugUpdate bugUpdate) {

        Optional<Bug> sameBug = bugService.update(
                bugUpdate.getBug(),
                bugUpdate.getNewTriedSolution(),
                null
        );

        int statusCode;
        HttpStatus status;
        String name;

        if (sameBug.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            name = "BAD REQUEST";
        } else {
            status = OK;
            statusCode = OK.value();
            name = sameBug.get().getName();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Bug", sameBug.orElse(null)))
                        .message("BUG UPDATED WITH NAME: " + name)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteBug(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        boolean isDeleted = bugService.delete(id);
        String deleteMessage = isDeleted ? "" : "NOT";

        if(isDeleted) {
            status = OK;
            statusCode = OK.value();
        } else {
            status = NOT_FOUND;
            statusCode = NOT_FOUND.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Bug", "DELETED: " + isDeleted))
                        .message("BUG " + deleteMessage + "DELETED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }
}
