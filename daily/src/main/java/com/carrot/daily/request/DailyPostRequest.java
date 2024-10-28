package com.carrot.daily.request;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.domain.Daily;

public record DailyPostRequest(
    Long userId,
    String categoryName,
    Long locationId,
    String title,
    String content

    ) {
    public Daily toEntity(DCategory dCategory) {
        return Daily.builder()
                .userId(userId)
                .locationId(locationId)
                .dCategory(dCategory)
                .title(title)
                .content(content)
                .build();
    }
}
