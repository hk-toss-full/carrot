package com.carrot.daily.request;

import com.carrot.daily.domain.Comment;
import com.carrot.daily.domain.Daily;

public record CommentRequest(
        Long userId,
        Long daylifeId,
        String content

) {
    public Comment toEntity() {
        return Comment.builder()
                .userId(userId)
                .daylifeId(daylifeId)
                .content(content)
                .build();
    }

}
