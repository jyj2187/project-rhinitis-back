package com.rhinitis.projectrhinitis.post.repository;

import com.rhinitis.projectrhinitis.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByName(String title);
}
