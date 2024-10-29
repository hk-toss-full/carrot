package com.carrot.chat.service;

import com.carrot.chat.domain.dto.ChatMessageResponse;
import com.carrot.chat.domain.dto.ChatRoomResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.ChatRoom;

import java.util.List;

public interface ChatService {

    List<ChatRoomResponse> getAllChatRooms();
    void saveMessage(ChatMessage chatMessage);
    ChatRoom findRoomById(String chatRoomId);
    List<ChatMessageResponse> getMessagesByRoomId(String chatRoomId);
}
