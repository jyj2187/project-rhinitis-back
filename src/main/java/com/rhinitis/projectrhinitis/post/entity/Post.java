package com.rhinitis.projectrhinitis.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    //private User user;
    //private Board board;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private PostStatus postStatus;

    @Builder
    public Post(Long userid, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus postStatus) {
        this.userid = userid;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postStatus = postStatus;
    }


}
