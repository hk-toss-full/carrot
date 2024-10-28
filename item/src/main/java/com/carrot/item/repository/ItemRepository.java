package com.carrot.item.repository;

import com.carrot.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategories_ScategoryId(Long scategoryId);
}
