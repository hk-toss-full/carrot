package com.carrot.daily.request;

import com.carrot.daily.domain.Daily;

public record DailyPostRequest(
    Long userId,
    Long categoryId,
    Long locationId,
    String title,
    String content

    ) {
    public Daily toEntity() {
        return Daily.builder()
                .userId(userId)
                .categoryId(categoryId)
                .locationId(locationId)
                .title(title)
                .content(content)
                .build();
    }
}
