package com.rhinitis.projectrhinitis.comment.dto;

import com.rhinitis.projectrhinitis.comment.entity.CommentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class Post{
        private String contents;

        @Builder
        public Post(String contents) {
            this.contents = contents;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Patch{
        private String contents;

        @Builder
        public Patch( String contents) {
            this.contents = contents;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long commentId;
        private String contents;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private CommentStatus commentStatus;

        @Builder
        public Response(Long commentId, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, CommentStatus commentStatus) {
            this.commentId = commentId;
            this.contents = contents;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.commentStatus = commentStatus;
        }
    }
}
