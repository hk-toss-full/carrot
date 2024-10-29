package com.carrot.chat.repository;

import com.carrot.chat.domain.entity.EnterChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnterChatRepository extends MongoRepository<EnterChat, String> {
}
