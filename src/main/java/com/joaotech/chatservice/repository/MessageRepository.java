package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageDocument, String> {

//    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);
//
//    List<ChatMessageDocument> findByChatId(String chatId);

    List<MessageDocument> findByRoomToken(String roomToken);

}