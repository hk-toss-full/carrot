package com.carrot.chat.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "chatMessages")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    private String chatMessageId;
    private String chatRoomId;
    private String message;
    private Boolean readOrNot;
    private Date createdAt;
    private Long userId;
    private MessageType type;
}
