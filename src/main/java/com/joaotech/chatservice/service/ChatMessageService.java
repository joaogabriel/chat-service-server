package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.repository.ChatMessageRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final MongoOperations mongoOperations;

    public ChatMessageService(ChatMessageRepository repository, ChatRoomService chatRoomService, MongoOperations mongoOperations) {
        this.repository = repository;
        this.chatRoomService = chatRoomService;
        this.mongoOperations = mongoOperations;
    }

    public ChatMessageDocument save(ChatMessageDocument chatMessageDocument) {
        chatMessageDocument.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessageDocument);
        return chatMessageDocument;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessageDocument> findChatMessages(String senderId, String recipientId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        List<ChatMessageDocument> messages = chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if (messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessageDocument findById(String id) {
        return repository
                .findById(id)
                .map(chatMessageDocument -> {
                    chatMessageDocument.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessageDocument);
                })
                .orElseThrow(() ->
                        new RuntimeException("can't find message (" + id + ")")); // alterei aqui
    }

    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        Query query = new Query(
                Criteria
                        .where("senderId").is(senderId)
                        .and("recipientId").is(recipientId));
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, ChatMessageDocument.class);
    }
}
