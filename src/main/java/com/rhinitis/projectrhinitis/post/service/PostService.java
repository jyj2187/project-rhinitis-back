package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostDto.Response addPost(PostDto.Post postDto);
    PostDto.Response getPost(Long postId);
    MultiResponseDto<PostDto.Response> getAllPost(Pageable pageable);
    PostDto.Response editPost(Long postId, PostDto.Patch patchDto);
    void deletePost(Long postId);
}
