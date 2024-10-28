package com.carrot.daily.service;

import com.carrot.daily.domain.DCategory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DCategoryService {
    Optional<DCategory> findByCategoryName(String categoryName);
}
