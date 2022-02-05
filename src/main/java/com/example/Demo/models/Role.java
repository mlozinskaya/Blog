package com.example.Demo.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
