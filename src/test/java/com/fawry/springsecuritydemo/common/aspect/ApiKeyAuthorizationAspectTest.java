package com.fawry.springsecuritydemo.common.aspect;

import com.fawry.springsecuritydemo.common.annotation.RequireRole;
import com.fawry.springsecuritydemo.common.exception.AuthorizationException;
import com.fawry.springsecuritydemo.common.servlet.HttpRequestHeaderUtil;
import com.fawry.springsecuritydemo.model.enumeration.Role;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApiKeyAuthorizationAspectTest {

    public static final String AUTHORIZATION = "Authorization";


    @Autowired
    private ApiKeyAuthorizationAspect apiKeyAuthorizationAspect;

    @MockitoBean
    private HttpRequestHeaderUtil httpRequestHeaderService;

    @MockitoBean
    private ProceedingJoinPoint proceedingJoinPoint;

    @MockitoBean
    private RequireRole requireRole;

    @Test
    void authorize_shouldPass_whenRoleIsValid() throws Throwable {
        // Arrange
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn("admin-key");
        when(requireRole.value()).thenReturn(Role.ADMIN);

        // Act
        apiKeyAuthorizationAspect.authorize(proceedingJoinPoint, requireRole);

        // Verify
        verify(proceedingJoinPoint).proceed();
    }

    @Test
    void authorize_shouldThrowAuthorizationException_whenRoleIsInvalid() {
        // Arrange
        when(httpRequestHeaderService.getHeader(AUTHORIZATION)).thenReturn("user-key");
        when(requireRole.value()).thenReturn(Role.ADMIN);

        // Act & Assert
        assertThatCode(() -> apiKeyAuthorizationAspect.authorize(proceedingJoinPoint, requireRole))
                .isInstanceOf(AuthorizationException.class);
    }


}
