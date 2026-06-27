package com.fawry.springsecuritydemo.repository;

import com.fawry.springsecuritydemo.model.User;
import com.fawry.springsecuritydemo.model.enumeration.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryStub implements UserRepository{ // Stub implementation of UserRepository for testing purposes
    @Override
    public List<User> findAll() {
        return List.of(
                new User("ahmed", "123", List.of(Role.USER),
                        new com.fawry.springsecuritydemo.model.Merchant(1, "Ahmed Shop", "Grocery")),

                new User("sara", "123", List.of(Role.USER),
                        new com.fawry.springsecuritydemo.model.Merchant(2, "Sara Store", "Fashion")),

                new User("admin", "admin123", List.of(Role.ADMIN),
                        new com.fawry.springsecuritydemo.model.Merchant(3, "Admin Hub", "System"))
        );
    }
}
