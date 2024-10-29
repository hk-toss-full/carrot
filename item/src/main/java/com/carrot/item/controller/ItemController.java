package com.carrot.item.controller;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;
import com.carrot.item.global.ApplicationResponse;
import com.carrot.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApplicationResponse<List<ItemResponse>>> getAllItems() {
        List<ItemResponse> items = itemService.getAllItems();
        return ResponseEntity.ok(ApplicationResponse.success(items));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> getItem(@PathVariable Long itemId) {
        ItemResponse itemResponse = itemService.getItem(itemId);
        return ResponseEntity.ok(ApplicationResponse.success(itemResponse));
    }

    @GetMapping("/my-items")
    public ResponseEntity<ApplicationResponse<List<ItemResponse>>> getMyItems(
            @RequestHeader("Authorization") String token) {
        Long userId = itemService.verifyToken(token);
        List<ItemResponse> items = itemService.getMyItems(userId);
        return ResponseEntity.ok(ApplicationResponse.success(items));
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse<ItemResponse>> addItem(
            @RequestHeader("Authorization") String token, @RequestBody ItemRequest itemRequest) {
        Long userId = itemService.verifyToken(token);
        ItemResponse itemResponse = itemService.addItem(userId, itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApplicationResponse.success(itemResponse));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> updateItem(
            @RequestHeader("Authorization") String token,
            @PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {
        Long userId = itemService.verifyToken(token);
        ItemResponse itemResponse = itemService.updateItem(userId, itemId, itemRequest);
        return ResponseEntity.ok(ApplicationResponse.success(itemResponse));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> deleteItem(
            @RequestHeader("Authorization") String token, @PathVariable Long itemId) {
        Long userId = itemService.verifyToken(token);
        itemService.deleteItem(userId, itemId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    @GetMapping("/categories/{sCategoryId}")
    public ResponseEntity<ApplicationResponse<List<ItemResponse>>> getItemsByCategory(@PathVariable Long sCategoryId) {
        List<ItemResponse> items = itemService.getItemsByCategory(sCategoryId);
        return ResponseEntity.ok(ApplicationResponse.success(items));
    }
}

