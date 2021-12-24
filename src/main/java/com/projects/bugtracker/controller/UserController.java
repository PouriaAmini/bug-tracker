package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.UserAccount;
import com.projects.bugtracker.model.update.UserRegistration;
import com.projects.bugtracker.model.update.UserUpdate;
import com.projects.bugtracker.service.UserAccountService;
import com.projects.bugtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAccountService userAccountService;

    @GetMapping("/find/{id}")
    ResponseEntity<Response> getUser(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        Optional<User> user = userService.getById(id);

        if(user.isPresent()) {
            status = OK;
            statusCode = OK.value();
        } else {
            status = NOT_FOUND;
            statusCode = NOT_FOUND.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", user))
                        .message("USER RETRIEVED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @GetMapping("/search/{name}")
    ResponseEntity<Response> searchUsers(@PathVariable String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Users", userService.searchUser(name)))
                        .message("USERS RETRIEVED WITH PATTERN: " + name)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/register")
    ResponseEntity<Response> createUser(@RequestBody @Valid UserRegistration user) {

        user.getUserAccount().setUser(user.getUser());
        user.getUserAccount().setEmail(user.getUser().getEmail());
        user.getUserAccount().setPosition(user.getUser().getPosition());
        user.getUser().setUserAccount(user.getUserAccount());

        Optional<User> newUser = userService.create(user.getUser());

        int statusCode;
        HttpStatus status;
        String email;

        if(newUser.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            email = "THE EMAIL ALREADY EXISTS";
        }
        else {
            status = CREATED;
            statusCode = CREATED.value();
            email = newUser.get().getEmail();
            userAccountService.create(newUser.get().getUserAccount());
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", newUser.isPresent() ? newUser.get() : "null"))
                        .message("USER CREATED WITH EMAIL: " + email)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateUser(@RequestBody @Valid UserUpdate userUpdate) {

        Optional<User> sameUser = userService.update(
                userUpdate.getUser(),
                userUpdate.getNewBug(),
                userUpdate.getNewGroup(),
                userUpdate.getNewProject(),
                userUpdate.getNewSocialLink()
        );

        int statusCode;
        HttpStatus status;
        String email;

        if(sameUser.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            email = "BAD REQUEST";
        }
        else {
            status = OK;
            statusCode = OK.value();
            email = sameUser.get().getEmail();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", sameUser.isPresent() ? sameUser.get() : "null"))
                        .message("USER UPDATED WITH EMAIL: " + email)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @PutMapping("/assign")
    ResponseEntity<Response> assignToUser(
            @RequestBody List<UUID> userIds,
            @RequestParam(value = "manager-id") UUID managerId,
            @RequestParam(value = "bug-id") UUID bugId
    ) {

        int statusCode = NOT_FOUND.value();
        HttpStatus status = NOT_FOUND;
        boolean isBugUpdated = false;
        boolean isAssigned = userService.assign(userIds, managerId, bugId);

        if(isAssigned) {
            statusCode = OK.value();
            status = OK;
            isBugUpdated = true;
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Users", "ASSIGNED: " + isBugUpdated))
                        .message("USERS " + (isAssigned ? "" : "NOT ") + "ASSIGNED TO BUG WITH ID: " + bugId)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Response> deleteUser(@PathVariable UUID id) {

        int statusCode;
        HttpStatus status;
        boolean isDeleted = userService.delete(id);
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
                        .data(Map.of("User", "DELETED: " + isDeleted))
                        .message("USER " + deleteMessage + "DELETED WITH ID: " + id.toString())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }
}
