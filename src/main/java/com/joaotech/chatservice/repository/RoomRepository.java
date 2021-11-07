package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomModel;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends CassandraRepository<RoomModel, UUID> {

    @Query("SELECT * FROM rooms WHERE sender_token=?0 AND recipient_token=?1 AND is_closed=false")
    Optional<RoomModel> findBySenderTokenAndRecipientTokenAndClosedIsFalse(String senderToken, String recipientToken);

    @Query("SELECT * FROM rooms WHERE recipient_token=?0 AND is_closed=false ALLOW FILTERING")
    List<RoomModel> findByRecipientTokenAndClosedIsFalse(String recipientToken);

    @Query("SELECT * FROM rooms WHERE sender_token=?0 AND is_closed=false")
    List<RoomModel> findBySenderTokenAndClosedIsFalse(String recipientToken);

}
