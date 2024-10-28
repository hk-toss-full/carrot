package com.carrot.daily.service;

import com.carrot.daily.domain.Daily;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DailyService {
    void dailyPost(DailyPostRequest dailyPostRequest);
    List<DailyResponse> getAllPosts();
    Optional<Daily> getPostById(Long id);
    void updateDailyPost(Long id, DailyPostRequest dailyPostRequest);
    void deleteDailyPost(Long id);
    List<DailyResponse> getPostsByCategoryName(String categoryName);

}
