package com.carrot.daily.service;

import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;

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
        Optional<Daily> getPost = dailyRepository.findById(id);
        if(getPost.isPresent()){
            Daily daily=getPost.get();
            daily.updated(
                    dailyPostRequest.categoryId(),
                    dailyPostRequest.title(),
                    dailyPostRequest.content()
            );
            dailyRepository.save(daily);
        } else {
            throw new RuntimeException("등록되지 않은 게시물입니다.");
        }
    }

    @Override
    public void deleteDailyPost(Long id){
        if(dailyRepository.existsById(id)){
            dailyRepository.deleteById(id);
        }else{
            throw new RuntimeException("삭제할 게시글이 존재하지 않습니다.");
        }
    }

    @Override
    public List<DailyResponse> getPostsByCategoryId(Long categoryId) {
        List<Daily> dailies = dailyRepository.findAll();
        return DailyResponse.fromCategory(dailies, categoryId);
    }
}
