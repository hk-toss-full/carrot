package com.carrot.item.domain.dto;

import com.carrot.item.domain.entity.Item;
import com.carrot.item.domain.entity.ItemStatus;

import java.util.Date;

public record ItemRequest(
        Long userId,
        String itemTitle,
        String itemContent,
        ItemStatus status
) {
    public Item toEntity() {
        return Item.builder()
                .userId(userId)
                .itemTitle(itemTitle)
                .itemContent(itemContent)
                .createdAt(new Date())
                .updatedAt(new Date())
                .status(ItemStatus.AVAILABLE)
                .build();
    }
}
