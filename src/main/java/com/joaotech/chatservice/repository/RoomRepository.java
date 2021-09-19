package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomModel;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomModel, String> {

    @Query("SELECT * FROM chat_service.room WHERE id=?0 ALLOW FILTERING")
    Optional<RoomModel> findById(String token);

    @Query("SELECT * FROM chat_service.room WHERE sender_token=?0 AND recipient_token=?1 ALLOW FILTERING")
    Optional<RoomModel> findBySenderTokenAndRecipientToken(String senderId, String recipientId);

    @Query("SELECT * FROM chat_service.room WHERE sender_token=?0 AND closed_on='' ALLOW FILTERING")
    List<RoomModel> findBySenderTokenAndRecipientTokenAndIsClosed(String senderId);

    @Query("SELECT * FROM chat_service.room WHERE recipient_token=?0 AND isClosed ALLOW FILTERING")
    List<RoomModel> findByRecipientTokenAndIsCLosed(String recipientToken);

}
