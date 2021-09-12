package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findByToken(String token);

    Optional<Room> findBySenderTokenAndRecipientTokenAndClosedOnIsNull(String senderToken, String recipientToken);

    List<Room> findBySenderTokenAndClosedOnIsNull(String senderToken);

    List<Room> findByRecipientTokenAndClosedOnIsNull(String recipientToken);

}
