package com.rhinitis.projectrhinitis;

import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.entity.MemberStatus;
import com.rhinitis.projectrhinitis.member.entity.Role;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class TestData {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    void init() {
        String password = "12345";
        String encPassword = passwordEncoder.encode(password);

        Member kkh = memberRepository.save(Member.builder()
                .username("rlghd153")
                .password(encPassword)
                .aboutMe("김기홍")
                .displayName("김기홍")
                .email("rlghd153@gmail.com")
                .memberRole(Role.MEMBER)
                .memberStatus(MemberStatus.ACTIVE).build());
        Member kdk = memberRepository.save(Member.builder()
                .username("liean17")
                .password(encPassword)
                .aboutMe("강도경")
                .displayName("강도경")
                .email("liean17@gmail.com")
                .memberRole(Role.MEMBER)
                .memberStatus(MemberStatus.ACTIVE).build());
        Member jyj = memberRepository.save(Member.builder()
                .username("jyj2187")
                .password(encPassword)
                .aboutMe("정윤조")
                .displayName("정윤조")
                .email("jyjgom@gmail.com")
                .memberRole(Role.MEMBER)
                .memberStatus(MemberStatus.ACTIVE).build());

        Member[] members = new Member[]{kkh, kdk, jyj};

        for (int i = 0; i < 50; i++) {
            StringBuilder title = new StringBuilder("제목입니다.");
            StringBuilder content = new StringBuilder("내용입니다.");
            postRepository.save(Post.builder()
                    .title(title.append(i).toString())
                    .content(content.append(i).toString())
                    .postStatus(PostStatus.ACTIVE)
                    .member(members[i%3]).build());
        }
    }
}
