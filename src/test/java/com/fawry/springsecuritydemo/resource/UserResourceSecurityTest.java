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

    public static final String ADMIN_KEY = "admin-key";
    public static final String ANY_KEY = "any-key";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Nested
    class AuthenticateUserTest {
        @Test
        void shouldReturnOk_whenUserKeyAllowed() throws Exception {
            mockMvc.perform(get("/api/users")
                    .header("Authorization", ADMIN_KEY)).andExpect(status().isOk());
        }

        @Test
        void shouldReturnUnauthorized_whenUserKeyIsNotAllowed() throws Exception {
            mockMvc.perform(get("/api/users")
                    .header("Authorization", ANY_KEY)).andExpect(status().isUnauthorized());
        }

        @Test
        void shouldReturnUnauthorized_whenUserKeyIsMissing() throws Exception {
            mockMvc.perform(get("/api/users")).andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class AuthorizeUserTest {

        @Test
        void findUsers_shouldReturnForbidden_whenUserIsNotAdmin() throws Exception {
            mockMvc.perform(
                    get("/api/users").header("Authorization", "user-key")
            ).andExpect(status().isForbidden());
        }
    }

}
