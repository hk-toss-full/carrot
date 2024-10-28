package com.carrot.daily.controller;

import com.carrot.daily.domain.DCategory;
import com.carrot.daily.domain.Daily;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import com.carrot.daily.service.DCategoryService;
import com.carrot.daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/daily")
public class DailyController {
    private final DailyService dailyService;
    private final DCategoryService dCategoryService;

    public DailyController(DailyService dailyService, DCategoryService dCategoryService) {
        this.dailyService = dailyService;
        this.dCategoryService = dCategoryService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<DailyResponse>> getAllPosts() {
        List<DailyResponse> postsList = dailyService.getAllPosts();
        return ResponseEntity.ok(postsList);
    }

    @GetMapping("/post/{postId}")
    public Optional<DailyResponse> getPostById(@PathVariable Long postId) {
        return dailyService.getPostById(postId)
                .map(DailyResponse::from);
    }

    @PostMapping("/write-post")
    public ResponseEntity<String> writePost(@RequestBody DailyPostRequest dailyPostRequest) {
        dailyService.dailyPost(dailyPostRequest);
        return ResponseEntity.ok("게시글이 등록되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDailyPost(@PathVariable Long id, @RequestBody DailyPostRequest dailyPostRequest) {
        dailyService.updateDailyPost(id, dailyPostRequest);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDailyPost(@PathVariable Long id){
        dailyService.deleteDailyPost(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    @GetMapping("/category")
    public ResponseEntity<List<DailyResponse>> getPostsByCategoryName(@RequestParam String categoryName) {
        Optional<DCategory> dCategory = dCategoryService.findByCategoryName(categoryName);
        List<Daily> dailies = dCategory.get().getDailies();
        List<DailyResponse> dailyResponses = DailyResponse.fromList(dailies);
        return ResponseEntity.ok(dailyResponses);
    }
}
