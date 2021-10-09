package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomModel;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<RoomModel, String> {

    @Query("SELECT * FROM room WHERE sender_token=?0 AND recipient_token=?1 AND is_closed=false")
    Optional<RoomModel> findBySenderTokenAndRecipientTokenAndClosedIsFalse(String senderToken, String recipientToken);

    @Query("SELECT * FROM room WHERE recipient_token=?0 AND is_closed=false")
    List<RoomModel> findByRecipientTokenAndClosedIsFalse(String recipientToken);

    @Query("SELECT * FROM room WHERE sender_token=?0 AND is_closed=false")
    List<RoomModel> findBySenderTokenAndClosedIsFalse(String recipientToken);



}
