package com.rhinitis.projectrhinitis.comment.entity;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.util.audit.Auditable;
import com.rhinitis.projectrhinitis.util.exception.BusinessLogicException;
import com.rhinitis.projectrhinitis.util.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String content;
    private CommentStatus commentStatus;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(Long commentId, String content, CommentStatus commentStatus, Member member, Post post) {
        this.commentId = commentId;
        this.content = content;
        this.commentStatus = commentStatus;
        this.member = member;
        this.post = post;
    }

    public void update(CommentDto.Patch patchDto){
        this.content = patchDto.getContent();
    }
    public void changeStatus(CommentStatus status){
        this.commentStatus = status;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public void checkPermission(Member authenticatedMember) {
        if(!this.member.getMemberId().equals(authenticatedMember.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.PERMISSION_DENIED);
        }
    }
}
