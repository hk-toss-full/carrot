package com.carrot.chat.handler;

import com.carrot.chat.domain.dto.ChatMessageRequest;
import com.carrot.chat.domain.dto.UserResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.ChatRoom;
import com.carrot.chat.domain.entity.MessageType;
import com.carrot.chat.kafka.UserServiceClient;
import com.carrot.chat.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final UserServiceClient userServiceClient;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessageRequest chatMessageRequest = objectMapper.readValue(payload, ChatMessageRequest.class);

        ChatRoom room = chatService.findRoomById(chatMessageRequest.chatRoomId());
        Set<WebSocketSession> sessions = room.getSessions();

        UserResponse userResponse = userServiceClient.getUserById(chatMessageRequest.userId());
        String userName = userResponse != null ? userResponse.userId().toString() : "Unknown User";

        ChatMessage chatMessage;

        if (chatMessageRequest.type().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage = ChatMessage.builder()
                    .chatMessageId(null)
                    .chatRoomId(chatMessageRequest.chatRoomId())
                    .message(userName + "님이 입장했습니다.")
                    .readOrNot(false)
                    .createdAt(new Date())
                    .userId(chatMessageRequest.userId())
                    .type(MessageType.TEXT)
                    .build();

            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            log.info("User entered: {}", userName);
        } else if (chatMessageRequest.type().equals(MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage = ChatMessage.builder()
                    .chatMessageId(null)
                    .chatRoomId(chatMessageRequest.chatRoomId())
                    .message(userName + "님이 퇴장했습니다.")
                    .readOrNot(false)
                    .createdAt(new Date())
                    .userId(chatMessageRequest.userId())
                    .type(MessageType.TEXT)
                    .build();

            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            log.info("User left: {}", userName);
        } else {
            chatMessage = chatMessageRequest.toEntity();
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }
    }

    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream().forEach(roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }
}
