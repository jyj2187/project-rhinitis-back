package com.rhinitis.projectrhinitis.comment.repository;

import com.rhinitis.projectrhinitis.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
