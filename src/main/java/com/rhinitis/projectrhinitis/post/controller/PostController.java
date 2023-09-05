package com.rhinitis.projectrhinitis.post.controller;

import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImpl postService;
    //TODO 도메인 정하기

    //글 등록폼
    @GetMapping("/addPost")
    public String getPostForm(@PathVariable Long postId){
        return "board/postPost";
    }
    //글 등록
    @PostMapping("/{postId}")
    public String postPost(@Validated @ModelAttribute("post")PostDto.Post postDto, BindingResult bindingResult, @PathVariable Long postId, Model model){
        if(bindingResult.hasErrors()){
            return "board/postPost";
        }
        PostDto.Response postResponse = postService.addPost(postDto);
        return "redirect:/board/" + postResponse.getPostId();
    }
    //글 조회
    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model){
        PostDto.Response postResponse = postService.getPost(postId);
        model.addAttribute("post",postResponse);
        return "board/post";
    }
    //글 다수 조회

    //글 수정 폼
    @GetMapping("/{postId}")
    public String getEditForm(@PathVariable Long postId, Model model){
        PostDto.Response postResponse = postService.getPost(postId);
        model.addAttribute("post",postResponse);
        return "board/editPost";
    }

    //글 수정
    @PostMapping("/edit/{postId}")
    public String patchPost(@PathVariable Long postId, PostDto.Patch patchDto, Model model){
        PostDto.Response postResponse = postService.editPost(postId,patchDto);
        model.addAttribute("post",postResponse);
        return "redirect:/board/" + postId;
    }

    //글 삭제
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "/board";
    }
}
