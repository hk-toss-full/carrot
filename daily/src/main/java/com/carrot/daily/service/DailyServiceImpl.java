package com.carrot.daily.service;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.DCategoryRepository;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;
    private final DCategoryRepository dCategoryRepository;

    public void dailyPost(DailyPostRequest dailyPostRequest) {
        String categoryName = dailyPostRequest.categoryName();
        if(categoryName == null || categoryName.isEmpty()){
            categoryName = "기타";
        }

        Optional<DCategory> dCategoryOpt = dCategoryRepository.findByName(categoryName);
        if (dCategoryOpt.isEmpty()) {
            throw new RuntimeException("존재하지 않는 카테고리입니다.");
        }

        DCategory dCategory = dCategoryOpt.get();
        Daily post = dailyPostRequest.toEntity(dCategory);
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
            Daily daily = getPost.get();

            Optional<DCategory> dCategoryOpt = dCategoryRepository.findByName(dailyPostRequest.categoryName());
            DCategory dCategory = dCategoryOpt.orElseThrow(() -> new RuntimeException("존재하지 않는 카테고리입니다."));

            daily.updated(
                    dCategory,
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
    public List<DailyResponse> getPostsByCategoryName(String categoryName){
        Optional<DCategory> dCategoryOption = dCategoryRepository.findByName(categoryName);
        if(dCategoryOption.isPresent()){
            DCategory dCategory = dCategoryOption.get();
            return dCategory.getDailies().stream()
                    .map(DailyResponse::from)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("카테고리가 존재하지 않습니다.");
        }
    }
}