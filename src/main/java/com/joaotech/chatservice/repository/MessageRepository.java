package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MessageRepository extends CrudRepository<MessageModel, UUID> {

    long countByRoomId(UUID roomId);

    long countByRoomIdAndCurrentStatus(UUID roomId, String status);

}
