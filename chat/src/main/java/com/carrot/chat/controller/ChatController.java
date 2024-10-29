package com.carrot.chat.controller;

import com.carrot.chat.domain.dto.ChatMessageRequest;
import com.carrot.chat.domain.dto.ChatMessageResponse;
import com.carrot.chat.domain.dto.ChatRoomResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.ChatRoom;
import com.carrot.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getAllChatRooms() {
        List<ChatRoomResponse> chatRooms = chatService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessageRequest chatMessageRequest) {
        chatService.saveMessage(chatMessageRequest.toEntity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(@PathVariable String chatRoomId) {
        List<ChatMessageResponse> messages = chatService.getMessagesByRoomId(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/room/{chatRoomId}")
    public ResponseEntity<ChatRoomResponse> getChatRoom(@PathVariable String chatRoomId) {
        ChatRoom room = chatService.findRoomById(chatRoomId);
        return ResponseEntity.ok(ChatRoomResponse.from(room));
    }
}
