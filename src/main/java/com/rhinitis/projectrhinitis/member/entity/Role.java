package com.rhinitis.projectrhinitis.member.entity;

public enum Role {
    ADMIN("관리자"),  MANAGER("매니저"), MEMBER("회원"), VISITOR("방문자");

    private String roleName;

    Role (String roleName) {
        this.roleName = roleName;
    }
}
