package com.fawry.springsecuritydemo.model;

import com.fawry.springsecuritydemo.model.enumeration.Role;

import java.util.List;

public record User(String name, String password, List<Role> roles, com.fawry.springsecuritydemo.model.Merchant merchant) {
}
