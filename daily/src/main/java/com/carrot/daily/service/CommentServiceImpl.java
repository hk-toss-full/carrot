package com.carrot.daily.service;

import com.carrot.daily.component.DeleteComment;
import com.carrot.daily.domain.Comment;
import com.carrot.daily.repository.CommentRepository;
import com.carrot.daily.request.CommentRequest;
import com.carrot.daily.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final DeleteComment deleteComment;

    @Override
    public void addComment(CommentRequest commentRequest) {
        Comment comment = commentRequest.toEntity();
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return CommentResponse.fromList(comments);
    }

    public void deleteComment(Long id) {
        deleteComment.deleteComment(id);
    }
}
