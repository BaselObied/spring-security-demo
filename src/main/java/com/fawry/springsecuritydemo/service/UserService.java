package com.fawry.springsecuritydemo.service;

import com.fawry.springsecuritydemo.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
}
