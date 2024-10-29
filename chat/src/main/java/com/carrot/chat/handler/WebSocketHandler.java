package com.carrot.chat.handler;

import com.carrot.chat.domain.dto.ChatMessageRequest;
import com.carrot.chat.domain.dto.ChatRoomRequest;
import com.carrot.chat.domain.dto.ItemResponse;
import com.carrot.chat.domain.dto.UserResponse;
import com.carrot.chat.domain.entity.ChatMessage;
import com.carrot.chat.domain.entity.MessageType;
import com.carrot.chat.kafka.ItemServiceClient;
import com.carrot.chat.kafka.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebSocketHandler {

    private final UserServiceClient userServiceClient;
    private final ItemServiceClient itemServiceClient;
    private final MongoTemplate mongoTemplate;

    private final Map<String, Set<WebSocketSession>> chatRoomSessions = new ConcurrentHashMap<>();

    @MessageMapping("/{roomId}")
    @SendTo("/pub/{roomId}")
    public ChatMessage handleTextMessage(@PathVariable String roomId,
                                         ChatMessageRequest chatMessageRequest,
                                         ChatRoomRequest chatRoomRequest,
                                         WebSocketSession session) throws Exception {
        UserResponse userResponse = userServiceClient.getUserById(chatMessageRequest.userId());
        String userNickname = userResponse != null ? userResponse.nickname() : "Unknown User";

        ItemResponse itemResponse = itemServiceClient.getItemById(chatRoomRequest.itemId());
        String itemTitle = itemResponse != null ? itemResponse.itemTitle() : null;

        Set<WebSocketSession> sessions = chatRoomSessions
                .computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet());

        ChatMessage chatMessage;

        if (chatMessageRequest.type().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage = ChatMessage.builder()
                    .chatRoomId(roomId)
                    .message(itemTitle + ":" + userNickname + "님이 입장했습니다.")
                    .readOrNot(false)
                    .createdAt(new Date())
                    .userId(chatMessageRequest.userId())
                    .type(MessageType.TEXT)
                    .build();

            mongoTemplate.save(chatMessage, "chatMessages");
            log.info("User entered: {}", userNickname);
        } else if (chatMessageRequest.type().equals(MessageType.QUIT)) {
            sessions.remove(session);
            chatMessage = ChatMessage.builder()
                    .chatRoomId(chatMessageRequest.chatRoomId())
                    .message(userNickname + "님이 퇴장했습니다.")
                    .readOrNot(false)
                    .createdAt(new Date())
                    .userId(chatMessageRequest.userId())
                    .type(MessageType.TEXT)
                    .build();

            mongoTemplate.save(chatMessage, "chatMessages");
            log.info("User left: {}", userNickname);
        } else {
            chatMessage = chatMessageRequest.toEntity();
            mongoTemplate.save(chatMessage, "chatMessages");
        }
        return chatMessage;
    }
}
