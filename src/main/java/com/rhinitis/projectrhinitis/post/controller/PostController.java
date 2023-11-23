package com.rhinitis.projectrhinitis.post.controller;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;

    //글 등록
    @PostMapping("/v1")
    public ResponseEntity postPost(@RequestBody PostDto.Save saveDto, Authentication authentication){
        PostDto.Response postResponse = postService.addPost(saveDto, authentication);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    //글 조회
    @GetMapping("/v1/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        PostDto.Response postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    //글 다수 조회
    @GetMapping("/v1")
    public ResponseEntity getAllPosts(@PageableDefault(page = 1, sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        MultiResponseDto responseDto = postService.getAllPost(pageable);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    //글 수정
    @PatchMapping("/v1/{postId}")
    public ResponseEntity patchPost(@PathVariable Long postId, @RequestBody PostDto.Patch patchDto, Authentication authentication){
        PostDto.Response postResponse = postService.editPost(postId,patchDto, authentication);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //글 삭제
    @DeleteMapping("/v1/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, Authentication authentication){
        postService.deletePost(postId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

