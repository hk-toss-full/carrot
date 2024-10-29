package com.carrot.chat.domain.dto;

import com.carrot.chat.domain.entity.ChatRoom;

import java.util.Date;

public record ChatRoomRequest(
        String chatRoomId,
        String name,
        Long itemId) {

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .name(name)
                .itemId(itemId)
                .createdAt(new Date())
                .build();
    }
}
