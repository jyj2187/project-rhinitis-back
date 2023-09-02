package com.rhinitis.projectrhinitis.post.dto;

import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostDto {
    @Getter
    @NoArgsConstructor
    public static class Post{
        private String title;
        private String contents;

        @Builder
        public Post(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Patch{
        private String title;
        private String contents;

        @Builder
        public Patch(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long postId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private PostStatus postStatus;

        @Builder
        public Response(Long postId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus postStatus) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.postStatus = postStatus;
        }
    }
}
