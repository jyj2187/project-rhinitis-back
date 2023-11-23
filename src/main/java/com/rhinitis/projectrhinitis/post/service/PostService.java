package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PostService {

    PostDto.Response addPost(PostDto.Save saveDto, Authentication authentication);
    PostDto.Response getPost(Long postId);
    MultiResponseDto<PostDto.Response> getAllPost(Pageable pageable);
    PostDto.Response editPost(Long postId, PostDto.Patch patchDto, Authentication authentication);
    void deletePost(Long postId, Authentication authentication);
}
