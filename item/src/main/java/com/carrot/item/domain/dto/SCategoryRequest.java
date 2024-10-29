package com.carrot.item.domain.dto;

import com.carrot.item.domain.entity.SCategory;

public record SCategoryRequest(Long sCategoryId, String category) {
    
    public SCategory toEntity() {
        return SCategory.builder()
                .sCategoryId(sCategoryId)
                .category(category)
                .build();
    }
}
