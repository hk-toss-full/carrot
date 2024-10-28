package com.carrot.daily.component;

import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.DailyPostRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateDailyPost {
    private final DailyRepository dailyRepository;

    public UpdateDailyPost(DailyRepository dailyRepository){
        this.dailyRepository = dailyRepository;
    }

    public void updateDailyPost(Long id, DailyPostRequest dailyPostRequest) {
        Optional<Daily> getPost = dailyRepository.findById(id);
        if(getPost.isPresent()){
            Daily daily = getPost.get();
            updatePost(daily, dailyPostRequest);
            dailyRepository.save(daily);
        } else {
            throw new RuntimeException("등록되지 않은 게시물입니다.");
        }
    }
    private void updatePost(Daily daily, DailyPostRequest dailyPostRequest){
        daily.updated(
                dailyPostRequest.categoryId(),
                dailyPostRequest.title(),
                dailyPostRequest.content()
        );
    }

}
