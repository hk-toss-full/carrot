package com.carrot.daily.controller;

import com.carrot.daily.request.CommentRequest;
import com.carrot.daily.response.CommentResponse;
import com.carrot.daily.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/daily")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{dailyId}/comments")
    public ResponseEntity<String> writeComment(@RequestBody CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    @GetMapping("/{dailyId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByDailyId(){
        List<CommentResponse> commentsList = commentService.getAllComments();
        return ResponseEntity.ok(commentsList);
    }

    @DeleteMapping("/{dailyId}/comments/{commentsId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
