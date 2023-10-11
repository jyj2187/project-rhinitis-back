package com.rhinitis.projectrhinitis.comment.entity;

public enum CommentStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private String description;

    CommentStatus(String description) {
        this.description = description;
    }
}
