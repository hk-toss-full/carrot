package com.carrot.item.service;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();
    ItemResponse addItem(ItemRequest request);
    ItemResponse getItem(Long itemId);
    ItemResponse updateItem(Long itemId, ItemRequest request);
    void deleteItem(Long itemId);
    List<ItemResponse> getItemsByCategory(Long scategoryId);
}
