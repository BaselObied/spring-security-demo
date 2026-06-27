package com.fawry.springsecuritydemo.common.security.authentication.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class ApiKeyAuthentication implements Authentication {

    private final String apiKey;
    private final boolean isAuthenticated;
    private final List<GrantedAuthority> authorities;

    public ApiKeyAuthentication(String apiKey, boolean isAuthenticated, List<GrantedAuthority> authorities) {
        this.apiKey = apiKey;
        this.isAuthenticated = isAuthenticated;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public @Nullable Object getCredentials() {
        return this.apiKey;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
