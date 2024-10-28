package com.carrot.daily.response;

import com.carrot.daily.domain.Daily;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public record DailyResponse(
        Long userId,
        Long categoryId,
        Long locationId,
        String title,
        String content,
        Date createdAt
) {
        public static DailyResponse from(Daily daily){
            return new DailyResponse(
                    daily.getUserId(),
                    daily.getCategoryId(),
                    daily.getLocationId(),
                    daily.getTitle(),
                    daily.getContent(),
                    daily.getCreatedAt()
            );
        }

        public static List<DailyResponse> fromList(List<Daily> dailies) {
            return dailies.stream()
                    .map(DailyResponse::from)
                    .collect(Collectors.toList());
        }

        public static List<DailyResponse> fromCategory(List<Daily> dailies, Long categoryId) {
            return dailies.stream()
                    .filter(daily -> daily.getCategoryId().equals(categoryId))
                    .map(DailyResponse::from)
                    .collect(Collectors.toList());
        }
}
