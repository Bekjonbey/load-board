package com.example.transaction2.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum PermissionEnum implements GrantedAuthority {
    ADD_CARD, GET_USER_CARDS, GET_ALL_USER_CARDS, GET_USER_CARD, HOLD_TRANSACTION, CONFIRM_TRANSACTION,ADD_DRIVER;

    @Override
    public String getAuthority() {
        return name();
    }
}
