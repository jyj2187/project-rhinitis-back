package com.rhinitis.projectrhinitis.comment.service;

import com.rhinitis.projectrhinitis.comment.dto.CommentDto;
import com.rhinitis.projectrhinitis.comment.entity.Comment;
import com.rhinitis.projectrhinitis.comment.entity.CommentStatus;
import com.rhinitis.projectrhinitis.comment.repository.CommentRepository;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto.Response addComment(Long postId,CommentDto.Post commentDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment = dtoToComment(commentDto);

        //TODO post에 댓글 저장

        commentRepository.save(comment);
        CommentDto.Response response = mapToResponse(comment);

        log.info("게시글 id : {} , 게시글 명 : {}에 댓글이 달렸습니다. 댓글 내용 : {}",postId,post.getTitle(),comment.getContents());
        return response;
    }

    @Override
    public CommentDto.Response getComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        CommentDto.Response response = mapToResponse(comment);
        return response;
    }

    @Override
    public CommentDto.Response editComment(Long commentId, CommentDto.Patch patchDto) {
        Comment comment = getCommentById(commentId);
        comment.update(patchDto);
        CommentDto.Response response = mapToResponse(comment);
        return response;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.changeStatus(CommentStatus.INACTIVE);
        log.info("댓글 id : {} , {} 이(가) 비활성화 되었습니다.",commentId,comment.getContents());
    }

    private Comment dtoToComment(CommentDto.Post commentDto){
        Comment comment = Comment.builder()
                .contents(commentDto.getContents())
                .createdAt(LocalDateTime.now())
                .commentStatus(CommentStatus.ACTIVE)
                .build();
        return comment;
    }

    private CommentDto.Response mapToResponse(Comment comment){
        CommentDto.Response response = CommentDto.Response.builder()
                .commentId(comment.getCommentId())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .commentStatus(comment.getCommentStatus())
                .build();
        return response;
    }

    private Comment getCommentById(Long commentId){
        //TODO 모든 ElseThrow에 구체적인 오류 추가
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        return comment;
    }
}
