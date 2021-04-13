package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.ChatRoomDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoomDocument, String> {

    Optional<ChatRoomDocument> findByToken(String token);

    Optional<ChatRoomDocument> findBySenderTokenAndRecipientTokenAndClosedOnIsNull(String senderToken, String recipientToken);

}
