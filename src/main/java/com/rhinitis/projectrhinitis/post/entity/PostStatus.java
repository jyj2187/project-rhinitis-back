package com.rhinitis.projectrhinitis.post.entity;

public enum PostStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private String description;

    PostStatus(String description) {
        this.description = description;
    }
}
