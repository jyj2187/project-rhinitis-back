package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.post.dto.PostDto;

public interface PostService {

    PostDto.Response addPost();
    PostDto.Response getPost();
    //MultiResponseDto<PostDto.Response> getAllPost();
    PostDto.Response editPost();
    Long deletePost();
}
