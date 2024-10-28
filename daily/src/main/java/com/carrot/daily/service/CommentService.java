package com.carrot.daily.service;

import com.carrot.daily.request.CommentRequest;
import com.carrot.daily.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void addComment(CommentRequest commentRequest, Long daylifeId);
    List<CommentResponse> getAllComments(Long daylifeId);
    void deleteComment(Long daylifeId, Long commentId);
}

