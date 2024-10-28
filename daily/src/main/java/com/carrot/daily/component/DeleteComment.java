package com.carrot.daily.component;

import com.carrot.daily.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteComment {
    private final CommentRepository commentRepository;

    public void deleteComment(Long id){
        if(commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("삭제할 댓글이 존재하지 않습니다.");
        }
    }

}
