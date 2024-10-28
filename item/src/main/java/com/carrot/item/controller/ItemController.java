package com.carrot.item.controller;

import com.carrot.item.domain.dto.ItemRequest;
import com.carrot.item.domain.dto.ItemResponse;
import com.carrot.item.global.ApplicationResponse;
import com.carrot.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ApplicationResponse<ItemResponse>> addItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.addItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApplicationResponse.success(itemResponse));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> getItem(@PathVariable Long itemId) {
        ItemResponse itemResponse = itemService.getItem(itemId);
        return ResponseEntity.ok(ApplicationResponse.success(itemResponse));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> updateItem(
            @PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.updateItem(itemId, itemRequest);
        return ResponseEntity.ok(ApplicationResponse.success(itemResponse));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApplicationResponse<ItemResponse>> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(ApplicationResponse.success(null));
    }

    @GetMapping("/categories/{scategoryId}")
    public ResponseEntity<ApplicationResponse<List<ItemResponse>>> getItemsByCategory(@PathVariable Long scategoryId) {
        List<ItemResponse> items = itemService.getItemsByCategory(scategoryId);
        return ResponseEntity.ok(ApplicationResponse.success(items));
    }
}

