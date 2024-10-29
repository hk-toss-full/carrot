package com.carrot.chat.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "enterChats")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnterChat {

    @Id
    private String enterChatId;
    private String chatRoomId;
    private Long userId;
}
