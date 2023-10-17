package com.rhinitis.projectrhinitis.comment.service;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.dto.MultiResponseDto;

public interface CommentService {

    CommentDto.Response addComment(Long postId,CommentDto.Post commentDto);
    CommentDto.Response getComment(Long commentId);
    //MultiResponseDto<CommentDto.Response> getAllComment();
    CommentDto.Response editComment(Long commentId, CommentDto.Patch patchDto);
    void deleteComment(Long commentId);
}
