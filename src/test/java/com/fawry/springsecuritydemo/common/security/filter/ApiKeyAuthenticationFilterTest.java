package com.fawry.springsecuritydemo.common.security.filter;

import com.fawry.springsecuritydemo.common.security.authentication.model.ApiKeyAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ApiKeyAuthenticationFilterTest {

    public static final String AUTHORIZATION = "Authorization";
    public static final String VALID_KEY = "admin-key";
    public static final String BAD_KEY = "bad-key";

    @InjectMocks
    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    void shouldAuthenticateAndContinueFilterChain() throws Exception {
        // Arrange
        when(request.getHeader(AUTHORIZATION)).thenReturn(VALID_KEY);

        ApiKeyAuthentication authenticated = new ApiKeyAuthentication(VALID_KEY, false, List.of());

        when(authenticationManager.authenticate(any(ApiKeyAuthentication.class)))
                .thenReturn(authenticated);

        // Act
        apiKeyAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(authenticationManager).authenticate(any(ApiKeyAuthentication.class));

        verify(filterChain).doFilter(request, response);

        verify(response, never()).sendError(anyInt(), anyString());

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();

    }

    @Test
    void shouldReturnErrorUnauthorize_whenAuthenticationFails() throws Exception {
        // Arrange
        when(request.getHeader(AUTHORIZATION))
                .thenReturn(BAD_KEY);

        when(authenticationManager.authenticate(any(ApiKeyAuthentication.class)))
                .thenThrow(new AuthenticationException("Invalid API key") {
                });

        // Act
        apiKeyAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");

        verify(filterChain, never()).doFilter(any(), any());

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
