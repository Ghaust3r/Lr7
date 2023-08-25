package com.example.lr7.service;

import com.example.lr7.dto.UserDto;
import com.example.lr7.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
