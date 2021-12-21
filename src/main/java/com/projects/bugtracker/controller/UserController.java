package com.projects.bugtracker.controller;

import com.projects.bugtracker.model.Response;
import com.projects.bugtracker.model.User;
import com.projects.bugtracker.model.UserAccount;
import com.projects.bugtracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
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

    @PostMapping("/register/")
    ResponseEntity<Response> createUser(@RequestBody User user) {

        int statusCode;
        HttpStatus status;
        UserAccount userAccount = user.getUserAccount();
        userAccount.setUser(user);
        userAccount.setEmail(user.getEmail());

        if(
                user.getFirstName().equals("") ||
                user.getLastName().equals("") ||
                user.getEmail().equals("")
        ) {
            status = BAD_REQUEST;
            statusCode = BAD_REQUEST.value();
        } else {
            status = CREATED;
            statusCode = CREATED.value();
        }

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("User", user))
                        .message("USER CREATED WITH EMAIL: " + user.getEmail())
                        .status(status)
                        .statusCode(statusCode)
                        .build()
        );
    }

    @PutMapping
    User updateUser(@RequestBody User user) {
        return userService.update(user);
    }
}
