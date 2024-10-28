package com.carrot.daily.service;

import com.carrot.daily.request.CommentRequest;
import com.carrot.daily.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void addComment(CommentRequest commentRequest);
    List<CommentResponse> getAllComments();
    void deleteComment(Long id);
}

