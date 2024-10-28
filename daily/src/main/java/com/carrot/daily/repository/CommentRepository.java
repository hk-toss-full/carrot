package com.carrot.daily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carrot.daily.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDailyId(Long daylifeId);
}
