package com.carrot.item.service;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;
import com.carrot.item.domain.dto.KakaoTokenResponse;
import com.carrot.item.domain.entity.Item;
import com.carrot.item.exception.ItemErrorCode;
import com.carrot.item.exception.UserErrorCode;
import com.carrot.item.global.exception.ApplicationException;
import com.carrot.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    @Override
    public Long verifyToken(String token) {
        String url = "http://localhost:8081/api/v1/oauth/kakao/verify-token";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, KakaoTokenResponse.class);

        if (!response.getBody().success()) {
            throw new ApplicationException(UserErrorCode.INVALID_TOKEN);
        }

        return response.getBody().data();
    }

    @Override
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ApplicationException(ItemErrorCode.ITEM_NOT_FOUND));
        return ItemResponse.from(item);
    }

    @Override
    public ItemResponse addItem(Long userId, ItemRequest request) {
        Item item = request.toEntity(userId);
        Item savedItem = itemRepository.save(item);
        return ItemResponse.from(savedItem);
    }

    @Override
    public List<ItemResponse> getMyItems(Long userId) {
        return itemRepository.findByUserId(userId)
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponse updateItem(Long userId, Long itemId, ItemRequest request) {
        Item item = itemRepository.findByItemIdAndUserId(itemId, userId)
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
        itemRepository.save(updatedItem);
        return ItemResponse.from(updatedItem);
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        Item item = itemRepository.findByItemIdAndUserId(itemId, userId)
                .orElseThrow(() -> new ApplicationException(ItemErrorCode.ITEM_NOT_FOUND));
        itemRepository.delete(item);
    }

    @Override
    public List<ItemResponse> getItemsByCategory(Long sCategoryId) {
        return itemRepository.findByCategories_sCategoryId(sCategoryId)
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());
    }
}
