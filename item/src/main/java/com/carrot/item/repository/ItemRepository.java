package com.carrot.item.repository;

import com.carrot.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategories_sCategoryId(Long sCategoryId);
    List<Item> findByUserId(Long userId);
    Optional<Item> findByItemIdAndUserId(Long itemId, Long userId);
}
