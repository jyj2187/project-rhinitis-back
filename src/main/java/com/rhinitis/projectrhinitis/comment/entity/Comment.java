package com.rhinitis.projectrhinitis.comment.entity;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    //private User user;
    //private Post post;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private CommentStatus commentStatus;

    @Builder
    public Comment(Long commentId, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, CommentStatus commentStatus) {
        this.commentId = commentId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentStatus = commentStatus;
    }

    public void update(CommentDto.Patch patchDto){
        this.contents = patchDto.getContents();
        this.modifiedAt = LocalDateTime.now();
    }
    public void changeStatus(CommentStatus status){
        this.commentStatus = status;
    }
}
