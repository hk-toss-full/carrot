package com.carrot.daily.request;

import com.carrot.daily.domain.Comment;
import com.carrot.daily.domain.Daily;

public record CommentRequest(
        Long userId,
        String content

) {
    public Comment toEntity(Daily daily) {
        return Comment.builder()
                .daily(daily)
                .userId(userId)
                .content(content)
                .build();
    }
}
