package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.post.dto.PostDto;

public interface PostService {

    PostDto.Response addPost(PostDto.Post postDto);
    PostDto.Response getPost(Long postId);
    //MultiResponseDto<PostDto.Response> getAllPost();
    PostDto.Response editPost(Long postId, PostDto.Patch patchDto);
    void deletePost(Long postId);
}
