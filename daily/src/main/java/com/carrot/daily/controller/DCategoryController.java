package com.carrot.daily.controller;

import com.carrot.daily.response.DCategoryResponse;
import com.carrot.daily.service.DCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class DCategoryController {

    @Qualifier("DCategoryService")
    private final DCategoryService dCategoryService;

    @GetMapping
    public List<DCategoryResponse> getCategories() {
        return dCategoryService.getAllCategories();
    }
}