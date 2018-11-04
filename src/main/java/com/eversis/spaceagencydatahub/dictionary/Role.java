package com.eversis.spaceagencydatahub.dictionary;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("customer", "customer"),
    CONTENT_MANAGER("manager", "manager");

    private String roleName;
    private String password;

    Role(String roleName, String password) {
        this.roleName = roleName;
        this.password = password;
    }
}
