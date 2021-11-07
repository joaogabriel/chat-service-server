package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends CrudRepository<RoomDocument, UUID> {

    Optional<RoomDocument> findBySenderTokenAndRecipientTokenAndIsClosedIsFalse(String senderToken, String recipientToken);

    List<RoomDocument> findByRecipientTokenAndIsClosedIsFalse(String recipientToken);

    List<RoomDocument> findBySenderTokenAndIsClosedIsFalse(String recipientToken);

}
