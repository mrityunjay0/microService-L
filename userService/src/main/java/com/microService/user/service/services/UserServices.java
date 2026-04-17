package com.microService.user.service.services;

import com.microService.user.service.entity.User;

import java.util.List;

public interface UserServices {

    // CREATE USER
    User createUser(User user);

    // GET ALL USERS
    List<User> getAllUsers();

    // GET USER BY ID
    User getUserById(Long userId);

    // UPDATE USER
    User updateUser(User user, Long userId);

    // DELETE USER
    void deleteUser(Long userId);
}
