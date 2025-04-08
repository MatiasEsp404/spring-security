package com.matias.springjwt.security.dto;

public enum ERole {

    USER, MODERATOR, ADMIN;

    public String getRoleName() {
        return name();
    }

}