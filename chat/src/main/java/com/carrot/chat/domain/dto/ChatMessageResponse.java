package com.carrot.chat.domain.dto;

import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.MessageType;

import java.util.Date;

public record ChatMessageResponse(
        String chatMessageId,
        String chatRoomId,
        String message,
        Boolean readOrNot,
        Date createdAt,
        Long userId,
        MessageType type
) {

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getChatMessageId(),
                chatMessage.getChatRoomId(),
                chatMessage.getMessage(),
                chatMessage.getReadOrNot(),
                chatMessage.getCreatedAt(),
                chatMessage.getUserId(),
                chatMessage.getType()
        );
    }
}