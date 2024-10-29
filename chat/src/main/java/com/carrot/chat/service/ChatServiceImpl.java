package com.carrot.chat.service;

import com.carrot.chat.domain.dto.ChatMessageResponse;
import com.carrot.chat.domain.dto.ChatRoomResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.ChatRoom;
import com.carrot.chat.repository.ChatMessageRepository;
import com.carrot.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatRoomResponse> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(ChatRoomResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoom findRoomById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
    }

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessageResponse> getMessagesByRoomId(String chatRoomId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomId(chatRoomId);
        return messages.stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toList());
    }
}