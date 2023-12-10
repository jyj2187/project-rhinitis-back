package com.rhinitis.projectrhinitis.post.service;

import com.rhinitis.projectrhinitis.dto.MultiResponseDto;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.entity.Role;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import com.rhinitis.projectrhinitis.post.dto.PostDto;
import com.rhinitis.projectrhinitis.post.entity.Post;
import com.rhinitis.projectrhinitis.post.entity.PostStatus;
import com.rhinitis.projectrhinitis.post.repository.PostRepository;
import com.rhinitis.projectrhinitis.util.exception.BusinessLogicException;
import com.rhinitis.projectrhinitis.util.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public PostDto.Response addPost(PostDto.Save saveDto) {
        Post post = saveDto.toEntity();
        Member member = memberRepository.findByUsername(saveDto.getUsername()).orElseThrow();
        if (member.getMemberRole().equals(Role.VISITOR)) {
            throw new BusinessLogicException(ExceptionCode.NO_POST_AUTHORIZATION);
        }
        post.setMember(member);
        postRepository.save(post);
        PostDto.Response response = new PostDto.Response(post);
        log.info("게시글 \"{}\" 이(가) 등록되었습니다. 게시글 ID : {}",post.getTitle(),post.getPostId());
        return response;
    }

    @Override
    public PostDto.Response getPost(Long postId) {
        Post post = getPostById(postId);
        PostDto.Response response = new PostDto.Response(post);
        return response;
    }

    @Override
    public MultiResponseDto getAllPost(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1,pageable.getPageSize(),pageable.getSort());
        Page<Post> postPage = postRepository.findAll(pageRequest);
        List<Post> posts = new ArrayList<>(postPage.getContent());
        List<PostDto.Response> responseList = posts.stream().map(PostDto.Response::new).collect(Collectors.toList());
        return new MultiResponseDto<>(responseList,postPage);
    }

    @Override
    public PostDto.Response editPost(Long postId, PostDto.Patch patchDto) {
        Post post = getPostById(postId);

        Member member = memberRepository.findByUsername(patchDto.getUsername()).orElseThrow(()->new BusinessLogicException(ExceptionCode.NO_MEMBER_EXIST));
        if (!member.getMemberId().equals(post.getMember().getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.NO_EDIT_AUTHORIZATION);
        }

        post.update(patchDto);
        postRepository.save(post);
        PostDto.Response response = new PostDto.Response(post);
        return response;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        post.changeStatus(PostStatus.INACTIVE);
        postRepository.save(post);
        log.info("게시글 \"{}\" 이(가) 비활성화 되었습니다. 게시글 ID : {}",post.getTitle(),postId);
    }

    private Post getPostById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow();
        return post;
    }
}
