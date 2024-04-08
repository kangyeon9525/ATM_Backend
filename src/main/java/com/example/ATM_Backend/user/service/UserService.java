package com.example.ATM_Backend.user.service;

import com.example.ATM_Backend.user.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
