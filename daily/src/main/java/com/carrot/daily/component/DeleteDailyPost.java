package com.carrot.daily.component;

import com.carrot.daily.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteDailyPost {
    private final DailyRepository dailyRepository;

    public void deletePost(Long id) {
        if(dailyRepository.existsById(id)) {
            dailyRepository.deleteById(id);
        } else {
            throw new RuntimeException("삭제할 게시글이 존재하지 않습니.");
        }
    }
}
