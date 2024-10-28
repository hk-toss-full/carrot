package com.carrot.daily.service;

import com.carrot.daily.domain.Comment;
import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.CommentRepository;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.CommentRequest;
import com.carrot.daily.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final DailyRepository dailyRepository;

    @Override
    public void addComment(CommentRequest commentRequest, Long daylifeId) {
        Daily daily = dailyRepository.findById(daylifeId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRequest.toEntity(daily);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getAllComments(Long daylifeId) {
        List<Comment> comments = commentRepository.findByDailyId(daylifeId);
        return CommentResponse.fromList(comments);
    }

    @Override
    public void deleteComment(Long daylifeId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));
        if(!comment.getDaily().getId().equals(daylifeId)){
            throw new RuntimeException("해당 게시글에 댓글이 존재하지 않습니다.");
        }
        commentRepository.delete(comment);
    }

}
