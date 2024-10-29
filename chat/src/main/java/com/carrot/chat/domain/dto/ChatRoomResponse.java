package com.carrot.chat.domain.dto;

import com.carrot.chat.domain.entity.ChatRoom;

import java.util.Date;

public record ChatRoomResponse(
        String chatRoomId,
        String name,
        Date createdAt,
        Long itemId
) {

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(chatRoom.getChatRoomId(),
                chatRoom.getName(),
                chatRoom.getCreatedAt(),
                chatRoom.getItemId());
    }
}
