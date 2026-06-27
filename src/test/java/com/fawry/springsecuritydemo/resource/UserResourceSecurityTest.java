package com.fawry.springsecuritydemo.resource;

import com.fawry.springsecuritydemo.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Nested
    class AuthenticateUserTest {
        @Test
        void shouldReturnOk_whenUserKeyAllowed() throws Exception {
            mockMvc.perform(get("/api/users")
                    .header("Authorization", "admin-key")).andExpect(status().isOk());
        }

        @Test
        void shouldReturnUnauthorized_whenUserKeyIsNotAllowed() throws Exception {
            mockMvc.perform(get("/api/users")
                    .header("Authorization", "invalid-key")).andExpect(status().isUnauthorized());
        }

        @Test
        void shouldReturnUnauthorized_whenUserKeyIsMissing() throws Exception {
            mockMvc.perform(get("/api/users")).andExpect(status().isUnauthorized());
        }
    }


}

