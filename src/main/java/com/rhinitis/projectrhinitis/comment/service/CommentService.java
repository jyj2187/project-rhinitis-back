package com.rhinitis.projectrhinitis.comment.service;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import org.springframework.security.core.Authentication;

public interface CommentService {

    CommentDto.Response addComment(Long postId,CommentDto.Post commentDto, Authentication authentication);
    CommentDto.Response getComment(Long commentId);
    //MultiResponseDto<CommentDto.Response> getAllComment();
    CommentDto.Response editComment(Long commentId, CommentDto.Patch patchDto, Authentication authentication);
    void deleteComment(Long commentId, Authentication authentication);
}
