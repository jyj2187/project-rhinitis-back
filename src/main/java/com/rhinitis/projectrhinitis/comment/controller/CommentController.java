package com.rhinitis.projectrhinitis.comment.controller;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/v1")
    public ResponseEntity postComment(@RequestParam Long postId, @RequestBody CommentDto.Post dto, Authentication authentication){
        CommentDto.Response response = commentService.addComment(postId, dto, authentication);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //댓글 조회
    @GetMapping("/v1/{commentId}")
    public ResponseEntity getComment(@PathVariable Long commentId){
        CommentDto.Response comment = commentService.getComment(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    //댓글 수정
    @PatchMapping("/v1/{commentId}")
    public ResponseEntity editComment(@PathVariable Long commentId, @RequestBody CommentDto.Patch dto, Authentication authentication){
        CommentDto.Response response = commentService.editComment(commentId, dto, authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/v1/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, Authentication authentication){
        commentService.deleteComment(commentId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
