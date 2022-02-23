package com.et.eatingtogether.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    CUSTOMER("ROLE_CUSTOMER"),
    STORE("ROLE_STORE"),
    ADMIN("ROLE_ADMIN");

    private String value;
}
