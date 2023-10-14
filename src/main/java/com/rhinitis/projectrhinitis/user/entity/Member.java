package com.rhinitis.projectrhinitis.user.entity;


import com.rhinitis.projectrhinitis.user.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String userId;
    private Long memberId;
    private String username;
    private String displayName;
    private String password;
    private String email;
    private String aboutMe;
//    private Role memberRole;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Builder
    public Member(Long memberId, String username, String displayName, String password, String email, String aboutMe, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.email = email;
        this.aboutMe = aboutMe;
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
}
