package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.UserAccount;
import com.projects.bugtracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    ResponseEntity<Response> createUser(@RequestBody @Valid User user) {

        UserAccount userAccount = user.getUserAccount();
        userAccount.setUser(user);
        userAccount.setEmail(user.getEmail());
        Optional<User> similarUser = userService.create(user);

        int statusCode;
        HttpStatus status;
        String email;

        if(similarUser.isEmpty()) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
            email = "THE EMAIL ALREADY EXISTS";
        }
        else {
            status = CREATED;
            statusCode = CREATED.value();
            email = similarUser.get().getEmail();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", user))
                        .message("USER CREATED WITH EMAIL: " + email)
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateUser(@RequestBody @Valid User user) {

        Optional<User> sameUser = userService.update(user);

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
