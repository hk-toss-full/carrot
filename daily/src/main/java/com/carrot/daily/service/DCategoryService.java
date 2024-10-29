package com.carrot.daily.service;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.response.DCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DCategoryService {
    Optional<DCategory> findByCategoryName(String categoryName);
    List<DCategoryResponse> getAllCategories();
}
