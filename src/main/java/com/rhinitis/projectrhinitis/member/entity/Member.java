package com.rhinitis.projectrhinitis.member.entity;


import com.rhinitis.projectrhinitis.comment.entity.Comment;
import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.util.audit.Auditable;
import com.rhinitis.projectrhinitis.util.exception.BusinessLogicException;
import com.rhinitis.projectrhinitis.util.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String userId;
    private Long memberId;
    private String username;
    private String displayName;
    private String password;
    private String email;
//    private Phone phone;
    private String aboutMe;
    @Enumerated(EnumType.STRING)
    private Role memberRole;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(Long memberId, String username, String displayName, String password, String email, String aboutMe, Role memberRole, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.email = email;
//        this.phone = phone;
        this.aboutMe = aboutMe;
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
    }

    public void updateMember(MemberDto.Patch patchDto) {
        this.aboutMe = patchDto.getAboutMe() == null ? aboutMe : patchDto.getAboutMe();
        this.email = patchDto.getEmail() == null ? email : patchDto.getEmail();
        this.displayName = patchDto.getDisplayName() == null ? displayName : patchDto.getDisplayName();
    }

    public void updatePassword() {

    }

    public void activate() {
        this.memberStatus = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        this.memberStatus = MemberStatus.INACTIVE;
    }

    public void checkInRegister() {
        if (this.memberStatus.equals(MemberStatus.ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_ACTIVATED);
        }
        if (!this.memberStatus.equals(MemberStatus.IN_REGISTER)) {
            // TODO: 세분화할 필요성.
            throw new BusinessLogicException(ExceptionCode.PERMISSION_DENIED);
        }
    }

    public void checkActive() {
        if (!this.memberStatus.equals(MemberStatus.ACTIVE)) {
            throw new BusinessLogicException(ExceptionCode.PERMISSION_DENIED);
        }
    }
}
