package ru.gil.securitydemo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.gil.securitydemo.model.Permission.DEVELOPERS_READ;
import static ru.gil.securitydemo.model.Permission.DEVELOPERS_WRITE;

public enum Role {
    USER(Set.of(DEVELOPERS_READ)), ADMIN(Set.of(DEVELOPERS_READ, DEVELOPERS_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
