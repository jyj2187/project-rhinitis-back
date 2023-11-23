package com.rhinitis.projectrhinitis;

import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectRhinitisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectRhinitisApplication.class, args);
    }

    @Bean
    public TestData dataInit(MemberRepository memberRepository,
                             PostRepository postRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new TestData(memberRepository, postRepository, bCryptPasswordEncoder);
    }
}
