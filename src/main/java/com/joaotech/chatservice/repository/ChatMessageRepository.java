package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {

    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

    List<ChatMessageDocument> findByChatId(String chatId);

}