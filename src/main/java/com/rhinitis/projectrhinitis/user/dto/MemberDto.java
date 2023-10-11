package com.rhinitis.projectrhinitis.user.dto;

import com.rhinitis.projectrhinitis.user.entity.Member;
import com.rhinitis.projectrhinitis.user.entity.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                    .build();
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

        @Builder
        public Response(Long memberId, String username, String displayName, String email, String aboutMe, MemberStatus memberStatus) {
            this.memberId = memberId;
            this.username = username;
            this.displayName = displayName;
            this.email = email;
            this.aboutMe = aboutMe;
            this.memberStatus = memberStatus;
        }

        public Response(Member member) {
            this.memberId = member.getMemberId();
            this.username = member.getUsername();
            this.displayName = member.getDisplayName();
            this.email = member.getEmail();
            this.aboutMe = member.getAboutMe();
            this.memberStatus = member.getMemberStatus();
        }
    }
}
