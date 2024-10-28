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

    @PostMapping("/{daylifeId}/comments")
    public ResponseEntity<String> writeComment(@RequestBody CommentRequest commentRequest, @PathVariable Long daylifeId) {
        commentService.addComment(commentRequest, daylifeId);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    @GetMapping("/{daylifeId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByDailyId(@PathVariable Long daylifeId){
        List<CommentResponse> commentsList = commentService.getAllComments(daylifeId);
        return ResponseEntity.ok(commentsList);
    }

    @DeleteMapping("/{daylifeId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long daylifeId, @PathVariable Long commentId){
        commentService.deleteComment(daylifeId, commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
