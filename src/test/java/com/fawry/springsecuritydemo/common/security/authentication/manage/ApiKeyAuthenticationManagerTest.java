package com.fawry.springsecuritydemo.common.security.authentication.manage;

import com.fawry.springsecuritydemo.common.exception.AuthenticationException;
import com.fawry.springsecuritydemo.common.exception.AuthorizationException;
import com.fawry.springsecuritydemo.common.security.authentication.model.ApiKeyAuthentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiKeyAuthenticationManagerTest {

    @InjectMocks
    private ApiKeyAuthenticationManager authenticationManager;

    @Mock
    private ApiKeyAuthenticationProvider authenticationProvider;

    @Test
    void authenticate_shouldReturnApiKeyAuthentication_whenAuthenticationSupported() {
        // Arrange
        ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication("key", false, List.of());
        when(authenticationProvider.supports(ApiKeyAuthentication.class)).thenReturn(true);

        // Act
        authenticationManager.authenticate(apiKeyAuthentication);

        // Assert
        verify(authenticationProvider).authenticate(apiKeyAuthentication);
    }

    @Test
    void authenticate_shouldThrowInternalAuthenticationServiceException_whenAuthenticationNotSupported() {
        // Arrange
        ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication("key", false, List.of());
        when(authenticationProvider.supports(any())).thenReturn(false);

        // Act + Assert
        verify(authenticationProvider, times(0)).authenticate(any());
        assertThatCode(() -> authenticationManager.authenticate(apiKeyAuthentication))
                .isInstanceOf(InternalAuthenticationServiceException.class);
    }
}
