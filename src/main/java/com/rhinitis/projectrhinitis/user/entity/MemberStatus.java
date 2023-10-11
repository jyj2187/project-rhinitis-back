package com.rhinitis.projectrhinitis.user.entity;

import lombok.Builder;

public enum MemberStatus {
    INACTIVE(0, "비활성"),
    ACTIVE(1 , "활성화"),
    IN_REGISTER(2, "가입중"),
    WITHDRAWAL(3, "휴면");

    private int statusCode;
    private String description;

    MemberStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
}
