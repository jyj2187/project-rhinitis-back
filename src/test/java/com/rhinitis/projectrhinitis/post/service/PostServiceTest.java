package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    Post post1 = Post.builder()
            .title("테스트제목")
            .content("테스트내용")
            .createdAt(LocalDateTime.of(2023,9,4,21,00))
            .modifiedAt(LocalDateTime.of(2023,10,5,21,00))
            .postStatus(PostStatus.ACTIVE)
            .build();

    PostDto.Post postDto = PostDto.Post.builder()
            .title("등록테스트제목")
            .contents("등록테스트내용")
            .build();

    PostDto.Patch patchDto = PostDto.Patch.builder()
            .title("수정테스트제목")
            .contents("수정테스트내용")
            .build();

    @Test
    void testTest(){
        //given
        String st = "테스트";
        //when
        String test = "테스트";
        //then
        assertThat(st).isEqualTo(test);
    }
}