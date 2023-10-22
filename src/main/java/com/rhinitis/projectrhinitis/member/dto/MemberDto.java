package com.rhinitis.projectrhinitis.member.dto;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.entity.MemberStatus;
import com.rhinitis.projectrhinitis.member.entity.Role;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Join{
        private String username;
        private String displayName;
        private String password;
        private String email;
        private String aboutMe;

        @Builder
        public Join(String username, String displayName, String password, String email, String aboutMe) {
            this.username = username;
            this.displayName = displayName;
            this.password = password;
            this.email = email;
            this.aboutMe = aboutMe;
        }

        public Member toEntity() {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .displayName(displayName)
                    .email(email)
                    .aboutMe(aboutMe)
                    .memberStatus(MemberStatus.IN_REGISTER)
                    .memberRole(Role.MEMBER)
                    .build();
        }

        public void encodePassword(PasswordEncoder encoder) {
            this.password = encoder.encode(this.password);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Login{
        private String username;
        private String password;

        @Builder
        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Activate{
        private String username;
        private String activationCode;

        @Builder
        public Activate(String username, String activationCode) {
            this.username = username;
            this.activationCode = activationCode;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Patch{
        private String displayName;
        private String email;
        private String aboutMe;

        @Builder
        public Patch(String displayName, String email, String aboutMe) {
            this.displayName = displayName;
            this.email = email;
            this.aboutMe = aboutMe;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Password{
        private String password;

        @Builder
        public Password(String password) {
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        private Long memberId;
        private String username;
        private String displayName;
        private String email;
        private String aboutMe;
        private MemberStatus memberStatus;
        private Role memberRole;

        @Builder
        public Response(Long memberId, String username, String displayName, String email, String aboutMe, MemberStatus memberStatus, Role memberRole) {
            this.memberId = memberId;
            this.username = username;
            this.displayName = displayName;
            this.email = email;
            this.aboutMe = aboutMe;
            this.memberStatus = memberStatus;
            this.memberRole = memberRole;
        }

        public Response(Member member) {
            this.memberId = member.getMemberId();
            this.username = member.getUsername();
            this.displayName = member.getDisplayName();
            this.email = member.getEmail();
            this.aboutMe = member.getAboutMe();
            this.memberStatus = member.getMemberStatus();
            this.memberRole = member.getMemberRole();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Info{
        private Long memberId;
        private String username;
        private String displayName;
        private String email;
        private String aboutMe;
        private MemberStatus memberStatus;
        private Role memberRole;
        private List<PostDto.Response> posts;
        private List<CommentDto.Response> comments;

        @Builder
        public Info(Long memberId, String username, String displayName, String email, String aboutMe, MemberStatus memberStatus, Role memberRole, List<PostDto.Response> posts, List<CommentDto.Response> comments) {
            this.memberId = memberId;
            this.username = username;
            this.displayName = displayName;
            this.email = email;
            this.aboutMe = aboutMe;
            this.memberStatus = memberStatus;
            this.memberRole = memberRole;
            this.posts = posts;
            this.comments = comments;
        }

        public Info(Member member) {
            this.memberId = member.getMemberId();
            this.username = member.getUsername();
            this.displayName = member.getDisplayName();
            this.email = member.getEmail();
            this.aboutMe = member.getAboutMe();
            this.memberStatus = member.getMemberStatus();
            this.memberRole = member.getMemberRole();
            this.posts = member.getPosts().stream().map(PostDto.Response::new).collect(Collectors.toList());
            this.comments = member.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
        }
    }
}

