package com.microService.user.service.controller;

import com.microService.user.service.entity.User;
import com.microService.user.service.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }


    // create users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    // get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    // get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }


    // update user by id
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
        User updatedUser = userService.updateUser(user,userId);
        return ResponseEntity.ok(updatedUser);
    }


    // delete user by id
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
