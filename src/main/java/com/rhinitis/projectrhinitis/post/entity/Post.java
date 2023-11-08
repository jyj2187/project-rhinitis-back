package com.rhinitis.projectrhinitis.post.entity;

import com.rhinitis.projectrhinitis.comment.entity.Comment;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.entity.Role;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.util.audit.Auditable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    //private User user;
    //private Board board;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();;

    @Builder
    public Post(Long postId, String title, String content, PostStatus postStatus, Member member) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.postStatus = postStatus;
        this.member = member;
    }

    public void update(PostDto.Patch patchDto){
        this.title = patchDto.getTitle();
        this.content = patchDto.getContent();
    }
    public void changeStatus(PostStatus status){
        this.postStatus = status;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void checkPermission(Member authenticatedMember) {
        if(Role.VISITOR.equals(authenticatedMember.getMemberRole())) {
            throw new RuntimeException("권한없음");
        }
        if(!this.member.getMemberId().equals(authenticatedMember.getMemberId())) {
            throw new RuntimeException("권한없음");
        }
    }
}
