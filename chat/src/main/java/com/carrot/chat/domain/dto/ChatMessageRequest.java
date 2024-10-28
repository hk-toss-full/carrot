package com.carrot.chat.domain.dto;

import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.MessageType;

import java.util.Date;

public record ChatMessageRequest(
        String chatRoomId,
        String message,
        Long userId,
        MessageType type
) {

    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .message(message)
                .readOrNot(false)
                .createdAt(new Date())
                .userId(userId)
                .type(type)
                .build();
    }
}
