package com.springsecurity.controller;

import com.springsecurity.dto.CreateUserRequest;
import com.springsecurity.model.User;
import com.springsecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(String username) {
        return ResponseEntity.ok(userService.login(username));
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
