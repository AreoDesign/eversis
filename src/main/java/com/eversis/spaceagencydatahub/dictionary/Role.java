package com.eversis.spaceagencydatahub.dictionary;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("customer", "customer_password"),
    MANAGER("content_manager", "manager_password");

    private String roleName;
    private String password;

    Role(String roleName, String password) {
        this.roleName = roleName;
        this.password = password;
    }
}
