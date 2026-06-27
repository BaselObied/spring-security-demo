package com.fawry.springsecuritydemo.common.security.authentication.manage;

import com.fawry.springsecuritydemo.common.security.authentication.model.ApiKeyAuthentication;
import com.fawry.springsecuritydemo.model.enumeration.Role;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth.keys}")
    List<String> validApiKeys;

    public ApiKeyAuthenticationProvider(List<String> validApiKeys) {
        this.validApiKeys = validApiKeys;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String headerKey = (String) authentication.getCredentials();

        if (!validApiKeys.contains(headerKey)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        GrantedAuthority authority = getGrantedAuthority(headerKey);

        return new ApiKeyAuthentication(headerKey, true, List.of(authority));
    }


    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }

    private static GrantedAuthority getGrantedAuthority(String headerKey) {
        Role role = "admin-key".equals(headerKey) ? Role.ADMIN : Role.USER;
        return new SimpleGrantedAuthority("ROLE_" + role.name());
    }
}
