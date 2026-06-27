package com.fawry.springsecuritydemo.service;

import com.fawry.springsecuritydemo.model.User;
import com.fawry.springsecuritydemo.model.enumeration.Role;
import com.fawry.springsecuritydemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void getUsers_shouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = List.of(
                new User("ahmed", "123", List.of(Role.USER),
                        new com.fawry.springsecuritydemo.model.Merchant(1, "Ahmed Shop", "Grocery")),

                new User("sara", "123", List.of(Role.USER),
                        new com.fawry.springsecuritydemo.model.Merchant(2, "Sara Store", "Fashion")),

                new User("admin", "admin123", List.of(Role.ADMIN),
                        new com.fawry.springsecuritydemo.model.Merchant(3, "Admin Hub", "System"))
        );

        when(userRepository.findAll()).thenReturn(expectedUsers);

        //Act
        List<User> actualUsers = userService.getUsers();

        // Assert
        verify(userRepository).findAll();
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }
}
