package com.carrot.item.domain.dto;

import com.carrot.item.domain.entity.Item;
import com.carrot.item.domain.entity.ItemStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record ItemRequest(
        String itemTitle,
        String itemContent,
        ItemStatus status,
        List<SCategoryRequest> categories
) {
    public Item toEntity(Long userId) {
        return Item.builder()
                .userId(userId)
                .itemTitle(itemTitle)
                .itemContent(itemContent)
                .createdAt(new Date())
                .updatedAt(new Date())
                .status(ItemStatus.AVAILABLE)
                .categories(categories.stream()
                        .map(SCategoryRequest::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
