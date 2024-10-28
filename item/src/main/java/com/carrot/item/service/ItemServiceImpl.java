package com.carrot.item.service;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;
import com.carrot.item.domain.entity.Item;
import com.carrot.item.exception.ItemErrorCode;
import com.carrot.item.global.exception.ApplicationException;
import com.carrot.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse addItem(ItemRequest request) {
        Item item = request.toEntity();
        Item savedItem = itemRepository.save(item);
        return ItemResponse.from(savedItem);
    }

    @Override
    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ApplicationException(ItemErrorCode.ITEM_NOT_FOUND));
        return ItemResponse.from(item);
    }

    @Override
    public ItemResponse updateItem(Long itemId, ItemRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ApplicationException(ItemErrorCode.ITEM_NOT_FOUND));
        Item updatedItem = Item.builder()
                .itemId(item.getItemId())
                .userId(item.getUserId())
                .itemTitle(request.itemTitle())
                .itemContent(request.itemContent())
                .createdAt(item.getCreatedAt())
                .updatedAt(new Date())
                .status(request.status())
                .build();
        itemRepository.save(item);
        return ItemResponse.from(updatedItem);
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ApplicationException(ItemErrorCode.ITEM_NOT_FOUND));
        itemRepository.delete(item);
    }

    @Override
    public List<ItemResponse> getItemsByCategory(Long scategoryId) {
        return itemRepository.findByCategories_ScategoryId(scategoryId)
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }
}
