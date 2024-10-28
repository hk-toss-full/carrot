package com.carrot.daily.repository;


import com.carrot.daily.domain.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {
//    List<Daily> findByCategoryId(Long categoryId);
}
