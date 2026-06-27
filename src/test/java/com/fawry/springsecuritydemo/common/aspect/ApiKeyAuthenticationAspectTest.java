package com.fawry.springsecuritydemo.common.aspect;

import com.fawry.springsecuritydemo.common.exception.AuthenticationException;
import com.fawry.springsecuritydemo.common.servlet.HttpRequestHeaderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ApiKeyAuthenticationAspectTest {

    public static final String AUTHORIZATION = "Authorization";


    @Autowired
    private ApiKeyAuthenticationAspect apiKeyAuthenticationAspect;

    @MockitoBean
    private HttpRequestHeaderUtil httpRequestHeaderService;

    @Value("${auth.keys}")
    private String[] validApiKeys;

    @Test
    void shouldPass_whenApiKeyIsValid() {
        // Arrange
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn(validApiKeys[0]);

        // Act + Assert
        assertThatCode(apiKeyAuthenticationAspect::authenticate).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowAuthenticationException_whenApiKeyIsInvalid() {
        // Arrange
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn("invalid-key");

        // Act + Assert
        assertThatCode(apiKeyAuthenticationAspect::authenticate).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void shouldThrowAuthenticationException_whenApiKeyIsNull() {
        // Arrange
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn(null);

        // Act + Assert
        assertThatCode(apiKeyAuthenticationAspect::authenticate).isInstanceOf(AuthenticationException.class);
    }

    @Test
    void shouldThrowAuthenticationException_whenApiKeyIsBlank() {
        // Arrange
        String blankApiKey = "   "; // A string with only whitespace
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn(blankApiKey);

        // Act + Assert
        assertThatCode(apiKeyAuthenticationAspect::authenticate).isInstanceOf(AuthenticationException.class);
    }

}
