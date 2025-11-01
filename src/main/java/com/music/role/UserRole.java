package com.music.role;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ADMIN"),
    USER_FULL("USER_FULL"),
    USER("USER");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
    
}
