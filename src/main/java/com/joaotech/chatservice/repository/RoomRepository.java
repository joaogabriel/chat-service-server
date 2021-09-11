package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<RoomDocument, String> {

    Optional<RoomDocument> findByToken(String token);

    Optional<RoomDocument> findBySenderTokenAndRecipientTokenAndClosedOnIsNull(String senderToken, String recipientToken);

    List<RoomDocument> findBySenderTokenAndClosedOnIsNull(String senderToken);

}
