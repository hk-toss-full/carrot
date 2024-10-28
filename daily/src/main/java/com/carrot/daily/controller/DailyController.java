package com.carrot.daily.controller;

import com.carrot.daily.domain.Daily;
import com.carrot.daily.repository.DailyRepository;
import com.carrot.daily.request.DailyPostRequest;
import com.carrot.daily.response.DailyResponse;
import com.carrot.daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService dailyService;
    private final DailyRepository dailyRepository;

    @Autowired
    public DailyController(DailyRepository dailyRepository, DailyService dailyService) {
        this.dailyRepository = dailyRepository;
        this.dailyService = dailyService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<DailyResponse>> getAllPosts() {
        List<DailyResponse> postsList = dailyService.getAllPosts();
        return ResponseEntity.ok(postsList);
    }

    @GetMapping("/posts/{id}")
    public Optional<Daily> getPostById(@PathVariable("id") Long id) {
        return dailyService.getPostById(id);
    }


    @PostMapping("/write")
    public ResponseEntity<String> writePost(@RequestBody DailyPostRequest dailyPostRequest) {
        dailyService.dailyPost(dailyPostRequest);
        return ResponseEntity.ok("게시글이 등록되었습니다.");
    }

    @PutMapping("/post-update/{id}")
    public ResponseEntity<String> updateDailyPost(@PathVariable Long id, @RequestBody DailyPostRequest dailyPostRequest) {
        dailyService.updateDailyPost(id, dailyPostRequest);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDailyPost(@PathVariable Long id){
        dailyService.deleteDailyPost(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<DailyResponse>> getPostsByCategoryId(@PathVariable Long categoryId) {
        List<DailyResponse> posts = dailyService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }
}
