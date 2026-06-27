package com.fawry.springsecuritydemo.common.aspect;

import com.fawry.springsecuritydemo.common.annotation.RequireRole;
import com.fawry.springsecuritydemo.common.exception.AuthorizationException;
import com.fawry.springsecuritydemo.common.servlet.HttpRequestHeaderUtil;
import com.fawry.springsecuritydemo.model.enumeration.Role;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiKeyAuthorizationAspect {

    public static final String AUTHORIZATION = "Authorization";

    private final HttpRequestHeaderUtil httpRequestHeaderUtil;

    public ApiKeyAuthorizationAspect(HttpRequestHeaderUtil httpRequestHeaderUtil) {
        this.httpRequestHeaderUtil = httpRequestHeaderUtil;
    }

    @Around("@annotation(requireRole)")
    public Object authorize(ProceedingJoinPoint proceedingJoinPoint, RequireRole requireRole) throws Throwable {
        String apiKey = httpRequestHeaderUtil.getHeader(AUTHORIZATION);
        Role role = switch (apiKey) {
            case "admin-key" -> Role.ADMIN;
            case "user-key" -> Role.USER;
            default -> Role.ANONYMOS;
        };

        if (role != requireRole.value()) {
            throw new AuthorizationException(HttpStatus.FORBIDDEN.name());
        }

        return proceedingJoinPoint.proceed();
    }
}
