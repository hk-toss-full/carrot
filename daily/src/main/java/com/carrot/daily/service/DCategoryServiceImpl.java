package com.carrot.daily.service;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.repository.DCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DCategoryServiceImpl implements DCategoryService{
    private final DCategoryRepository dCategoryRepository;

    public Optional<DCategory> findByCategoryName(String categoryName){
        return dCategoryRepository.findByName(categoryName);
    }
}
