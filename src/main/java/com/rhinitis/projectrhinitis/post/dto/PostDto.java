package com.rhinitis.projectrhinitis.post.dto;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Getter
    @NoArgsConstructor
    public static class Save {
        private String title;
        private String content;

        @Builder
        public Save(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .postStatus(PostStatus.ACTIVE)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Patch{
        private String title;
        private String content;

        @Builder
        public Patch(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long postId;
        private String title;
        private String content;
        private MemberDto.Response writer;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private PostStatus postStatus;
        private List<CommentDto.Response> comments;

        @Builder
        public Response(Long postId, String title, String content, MemberDto.Response writer, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus postStatus, List<CommentDto.Response> comments) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.writer = writer;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.postStatus = postStatus;
            this.comments = comments;
        }

        public Response(Post post) {
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.writer = new MemberDto.Response(post.getMember());
            this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
            this.postStatus = post.getPostStatus();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();
        }
    }
}
