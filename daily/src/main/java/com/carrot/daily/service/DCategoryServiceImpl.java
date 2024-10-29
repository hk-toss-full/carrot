package com.carrot.daily.service;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.repository.DCategoryRepository;
import com.carrot.daily.response.DCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DCategoryServiceImpl implements DCategoryService{
    private final DCategoryRepository dCategoryRepository;

    public Optional<DCategory> findByCategoryName(String categoryName){
        return dCategoryRepository.findByName(categoryName);
    }

    public List<DCategoryResponse> getAllCategories() {
        return dCategoryRepository.findAll().stream()
                .map(category -> new DCategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }
}
