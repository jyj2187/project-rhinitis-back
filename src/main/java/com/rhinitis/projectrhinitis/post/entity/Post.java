package com.rhinitis.projectrhinitis.post.entity;

import com.rhinitis.projectrhinitis.post.dto.PostDto;
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
    private Long postId;
    //private User user;
    //private Board board;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private PostStatus postStatus;

    @Builder
    public Post(Long postId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus postStatus) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postStatus = postStatus;
    }

    public void update(PostDto.Patch patchDto){
        this.title = patchDto.getTitle();
        this.content = patchDto.getContents();
        this.modifiedAt = LocalDateTime.now();
    }
    public void changeStatus(PostStatus status){
        this.postStatus = status;
    }


}
