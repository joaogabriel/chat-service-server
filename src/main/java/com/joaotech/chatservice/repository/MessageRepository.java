package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageDocument, String> {

    MessageDocument findByToken(String token);

    List<MessageDocument> findByRoomToken(String roomToken);

    long countByRoomTokenAndStatus(String roomToken, MessageStatus status);

//    List<ChatMessageDocument> findByChatId(String chatId);

}