package com.fawry.springsecuritydemo.common.aspect;

import com.fawry.springsecuritydemo.common.exception.AuthenticationException;
import com.fawry.springsecuritydemo.common.servlet.HttpRequestHeaderUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class ApiKeyAuthenticationAspect {

    public static final String AUTHORIZATION = "Authorization";
    private final HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Value("${auth.keys}")
    private List<String> validApiKeys;

    public ApiKeyAuthenticationAspect(HttpRequestHeaderUtil httpRequestHeaderUtil) {
        this.httpRequestHeaderUtil = httpRequestHeaderUtil;
    }


    @Before("@annotation(com.fawry.springsecuritydemo.common.annotation.Authenticate)")
    public void authenticate() {
        String apiKey = httpRequestHeaderUtil.getHeader(AUTHORIZATION);
        if (apiKey == null || !validApiKeys.contains(apiKey)) {
            throw new AuthenticationException("Not Authenticated");
        }
    }
}
