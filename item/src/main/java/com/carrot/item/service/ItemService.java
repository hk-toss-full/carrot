package com.carrot.item.service;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    Long verifyToken(String token);
    List<ItemResponse> getAllItems();
    ItemResponse getItem(Long itemId);
    ItemResponse addItem(Long userId, ItemRequest request);
    List<ItemResponse> getMyItems(Long userId);
    ItemResponse updateItem(Long userId, Long itemId, ItemRequest request);
    void deleteItem(Long userId, Long itemId);
    List<ItemResponse> getItemsByCategory(Long sCategoryId);
}
