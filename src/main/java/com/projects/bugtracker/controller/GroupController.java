package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Group;
import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.update.GroupUpdate;
import com.projects.bugtracker.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/find/{id}")
    ResponseEntity<Response> getGroup(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        Optional<Group> group = groupService.get(id);

        if(group.isPresent()) {
            status = OK;
            statusCode = OK.value();
        } else {
            status = NOT_FOUND;
            statusCode = NOT_FOUND.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("GROUP", group))
                        .message("GROUP RETRIEVED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @GetMapping("/search/{name}")
    ResponseEntity<Response> searchGroup(@PathVariable String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Groups", groupService.searchGroup(name)))
                        .message("GROUPS RETRIEVED WITH PATTERN: " + name)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/new")
    ResponseEntity<Response> createGroup(
            @RequestParam(value = "project-id")UUID projectId,
            @RequestBody Group group
    ) {

        int statusCode = NOT_FOUND.value();
        HttpStatus status = NOT_FOUND;
        Optional<Group> createdGroup = groupService.create(group, projectId);

        if(createdGroup.isPresent()) {
            status = CREATED;
            statusCode = CREATED.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("GROUP", group))
                        .message("GROUP CREATED WITH ID: " + group.getId())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateGroup(@RequestBody @Valid GroupUpdate groupUpdate) {

        Optional<Group> sameGroup = groupService.update(
                groupUpdate.getGroup(),
                groupUpdate.getNewContributor(),
                null
        );

        int statusCode;
        HttpStatus status;
        String name;

        if(sameGroup.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            name = "BAD REQUEST";
        }
        else {
            status = OK;
            statusCode = OK.value();
            name = sameGroup.get().getName();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Group", sameGroup.orElse(null)))
                        .message("GROUP UPDATED WITH NAME: " + name)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteGroup(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        boolean isDeleted = groupService.delete(id);
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
                        .data(Map.of("Group", "DELETED: " + isDeleted))
                        .message("GROUP " + deleteMessage + "DELETED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }
}
