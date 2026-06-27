package com.fawry.springsecuritydemo.repository;

import com.fawry.springsecuritydemo.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
