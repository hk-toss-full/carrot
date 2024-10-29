package com.carrot.chat.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "chatRooms")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String chatRoomId;
    private String name;
    private Date createdAt;
    private Long itemId;

    @Setter
    private Set<WebSocketSession> sessions = new HashSet<>();

    public Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
