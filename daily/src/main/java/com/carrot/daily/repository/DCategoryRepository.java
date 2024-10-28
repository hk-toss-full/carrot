package com.carrot.daily.repository;

import com.carrot.daily.domain.DCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DCategoryRepository extends JpaRepository<DCategory, Long> {
    Optional<DCategory> findByName(String categoryName);
}
