package com.carrot.item.domain.dto;

import com.carrot.item.domain.entity.Like;

public record LikeResponse(Long userId, Long itemId) {

    public static LikeResponse from(Like like) {
        return new LikeResponse(like.getUserId(), like.getItem().getItemId());
    }
}
