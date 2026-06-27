package com.fawry.springsecuritydemo.common.security.authentication.manage;

import com.fawry.springsecuritydemo.common.security.authentication.model.ApiKeyAuthentication;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ApiKeyAuthenticationProviderTest {

    private final ApiKeyAuthenticationProvider authenticationProvider =
            new ApiKeyAuthenticationProvider(List.of("admin-key", "user-key"));

    @Test
    void authenticate_shouldReturnAuthentication_whenTokenHasValidKey() {
        // Arrange
        Authentication token = new ApiKeyAuthentication("admin-key", false, List.of());

        // Act
        Authentication result = authenticationProvider.authenticate(token);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getCredentials()).isEqualTo("admin-key");
        assertThat(result.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_ADMIN");
    }

    @Test
    void authenticate_shouldThrowAuthenticationException_whenTokenHasInvalidKey() {
        // Arrange
        Authentication token =
                new ApiKeyAuthentication("invalid-key", false, List.of());

        // Act & Assert
        assertThatThrownBy(() -> authenticationProvider.authenticate(token))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid API Key");
    }

    @Test
    void supports_shouldReturnTrue_whenAuthenticationIsApiKeyAuthentication() {
        // Act
        boolean result = authenticationProvider.supports(ApiKeyAuthentication.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void supports_shouldReturnFalse_whenAuthenticationIsNotApiKeyAuthentication() {
        // Act
        boolean result = authenticationProvider.supports(Authentication.class);

        // Assert
        assertThat(result).isFalse();
    }
}