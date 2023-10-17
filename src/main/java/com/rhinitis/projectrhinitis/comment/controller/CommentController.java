package com.rhinitis.projectrhinitis.comment.controller;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/{postId}")
    public ResponseEntity postComment(@PathVariable Long postId, @RequestBody CommentDto.Post dto){
        CommentDto.Response response = commentService.addComment(postId, dto);
        System.out.println(postId);
        System.out.println("댓글 등록 시도");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity getComment(@PathVariable Long commentId){
        CommentDto.Response comment = commentService.getComment(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    //댓글 전체보기?


    //댓글 수정
    @PostMapping("/edit/{commentId}")
    public ResponseEntity editComment(@PathVariable Long commentId, @RequestBody CommentDto.Patch dto){
        CommentDto.Response response = commentService.editComment(commentId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 삭제
    @PostMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity("정상적으로 삭제되었습니다.",HttpStatus.OK);
    }
}
