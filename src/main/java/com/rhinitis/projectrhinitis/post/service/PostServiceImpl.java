package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public PostDto.Response addPost(PostDto.Post postDto) {
        Post post = dtoToPost(postDto);
        postRepository.save(post);
        PostDto.Response response = mapToResponse(post);
        log.info("게시글 \"{}\" 이(가) 등록되었습니다. 게시글 ID : {}",post.getTitle(),post.getPostId());
        return response;
    }

    @Override
    public PostDto.Response getPost(Long postId) {
        Post post = getPostById(postId);
        PostDto.Response response = mapToResponse(post);
        return response;
    }

    @Override
    public MultiResponseDto getAllPost() {
        List<Post> posts = postRepository.findAll();

        return new MultiResponseDto<>(posts);
    }

    @Override
    public PostDto.Response editPost(Long postId, PostDto.Patch patchDto) {
        Post post = getPostById(postId);
        post.update(patchDto);
        PostDto.Response response = mapToResponse(post);
        return response;
    }

    @Override
    public void deletePost(Long postId) {

        Post post = getPostById(postId);
        post.changeStatus(PostStatus.INACTIVE);
        log.info("게시글 \"{}\" 이(가) 비활성화 되었습니다. 게시글 ID : {}",post.getTitle(),postId);
    }

    private Post dtoToPost(PostDto.Post postDto){
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContents())
                .createdAt(LocalDateTime.now())
                .postStatus(PostStatus.ACTIVE)
                .build();
        return post;
    }

    private PostDto.Response mapToResponse(Post post){
        PostDto.Response response = PostDto.Response.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .postStatus(post.getPostStatus())
                .build();
        return response;
    }

    private Post getPostById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow();
        return post;
    }
}
