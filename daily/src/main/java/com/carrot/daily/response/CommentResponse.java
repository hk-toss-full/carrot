package com.carrot.daily.response;

import com.carrot.daily.domain.Comment;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse (
        Long userId,
        Long commentId,
        String content,
        Date createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getUserId(),
                comment.getDaily().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    public static List<CommentResponse> fromList(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }
}
