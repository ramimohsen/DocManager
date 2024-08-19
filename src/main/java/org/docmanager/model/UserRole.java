package org.docmanager.model;


import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_ADMIN,
    ROLE_CUSTOMER;

    public String cleanRolePrefix() {
        return this.toString().replaceFirst("^ROLE_", "");
    }
}
