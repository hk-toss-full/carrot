package com.carrot.daily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carrot.daily.domain.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
