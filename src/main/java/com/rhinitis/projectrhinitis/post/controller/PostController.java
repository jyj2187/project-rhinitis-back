package com.rhinitis.projectrhinitis.post.controller;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;
    //TODO 도메인 정하기

    //글 등록
    //ToDo +유저정보
    @PostMapping("/write")
    public ResponseEntity postPost(@RequestBody PostDto.Post postDto){
        PostDto.Response postResponse = postService.addPost(postDto);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
    //글 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        PostDto.Response postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    //글 다수 조회
    @GetMapping
    public ResponseEntity getAllPosts(@PageableDefault(sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        MultiResponseDto responseDto = postService.getAllPost(pageable);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    //글 수정 폼
    @GetMapping("/edit/{postId}")
    public ResponseEntity getEditForm(@PathVariable Long postId){
        PostDto.Response postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //글 수정
    @PostMapping("/edit/{postId}")
    public ResponseEntity patchPost(@PathVariable Long postId, @RequestBody PostDto.Patch patchDto){
        PostDto.Response postResponse = postService.editPost(postId,patchDto);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //글 삭제
    @PostMapping("/delete/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ResponseEntity("정상적으로 삭제되었습니다.",HttpStatus.OK);
    }
}
