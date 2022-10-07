package com.mysite.sbb.siteuser;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), //admin호출시 role_admin 나옴
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
