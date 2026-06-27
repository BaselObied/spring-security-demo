package com.fawry.springsecuritydemo.resource;

import com.fawry.springsecuritydemo.service.UserService;
import com.fawry.springsecuritydemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
//    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

}
