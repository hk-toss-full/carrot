package com.carrot.daily.service;

import com.carrot.daily.component.DeleteDailyPost;
import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.component.UpdateDailyPost;
import com.carrot.daily.response.DailyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;
    private final UpdateDailyPost updateDailyPost;
    private final DeleteDailyPost deleteDailyPost;

    public void dailyPost(DailyPostRequest dailyPostRequest) {
        Daily post = dailyPostRequest.toEntity();
        dailyRepository.save(post);
    }

    public List<DailyResponse> getAllPosts() {
        List<Daily> dailies = dailyRepository.findAll();
        return DailyResponse.fromList(dailies);
    }

    public Optional<Daily> getPostById(Long id){
        Optional<Daily> daily = dailyRepository.findById(id);
        return daily;
    }

    @Override
    public void updateDailyPost(Long id, DailyPostRequest dailyPostRequest) {
        updateDailyPost.updateDailyPost(id, dailyPostRequest);
    }

    @Override
    public void deleteDailyPost(Long id){
        deleteDailyPost.deletePost(id);
    }

    @Override
    public List<DailyResponse> getPostsByCategoryId(Long categoryId) {
        List<Daily> dailies = dailyRepository.findAll();
        return DailyResponse.fromCategory(dailies, categoryId);
    }
}
