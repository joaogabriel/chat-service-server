package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomModel;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomModel, String> {

    Optional<RoomModel> findByToken(String token);

    @Query("SELECT * FROM chat_service.room WHERE sender=?0 AND recipient=?1 ALLOW FILTERING")
    Optional<RoomModel> findBySenderAndRecipientToken(String senderToken, String recipientToken);

    @Query("SELECT * FROM chat_service.room WHERE sender=?0 AND closed_on='' ALLOW FILTERING")
    List<RoomModel> findBySenderAndRecipientTokenAndClosedOnIsNull(String senderToken);

    @Query("SELECT * FROM chat_service.room WHERE recipient=?0 AND closed_on='' ALLOW FILTERING")
    List<RoomModel> findByRecipientTokenAndClosedOnIsNull(String recipientToken);

}
