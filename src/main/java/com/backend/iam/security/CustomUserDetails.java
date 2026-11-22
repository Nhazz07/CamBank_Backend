package com.backend.iam.security;

import com.backend.iam.entity.User;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomerUserDetails implements UserDetails {

    private final Long userId;
    private final String email;
    private final String passwordHash;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomerUserDetails(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.passwordHash = user.getPasswordHash();
        this.active = "ACTIVE".equalsIgnoreCase(user.getStatus());
        this.authorities = user.getUserRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getRoleCode()))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
