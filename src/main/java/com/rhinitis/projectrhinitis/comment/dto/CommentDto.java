package com.rhinitis.projectrhinitis.comment.dto;

import com.rhinitis.projectrhinitis.comment.entity.Comment;
import com.rhinitis.projectrhinitis.comment.entity.CommentStatus;
import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @NoArgsConstructor
    public static class Post{
        private String content;
        /* 테스트 용 */
        private String username;

        @Builder
        public Post(String content, String username) {
            this.content = content;
            this.username = username;
        }

        public Comment toEntity() {
            return Comment.builder()
                    .content(content)
                    .commentStatus(CommentStatus.ACTIVE)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Patch{
        private String content;
        /* 테스트 용 */
        private String username;

        @Builder
        public Patch(String content, String username) {
            this.content = content;
            this.username = username;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long commentId;
        private String content;
        private MemberDto.Response writer;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private CommentStatus commentStatus;

        @Builder
        public Response(Long commentId, String content, MemberDto.Response writer, LocalDateTime createdAt, LocalDateTime modifiedAt, CommentStatus commentStatus) {
            this.commentId = commentId;
            this.content = content;
            this.writer = writer;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
            this.commentStatus = commentStatus;
        }

        public Response(Comment comment) {
            this.commentId = comment.getCommentId();
            this.content = comment.getContent();
            this.writer = new MemberDto.Response(comment.getMember());
            this.commentStatus = comment.getCommentStatus();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }
}
