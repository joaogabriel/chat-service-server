package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatusType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<MessageModel, UUID> {

    //    @Query("SELECT * FROM messages WHERE room_id=?0")
    List<MessageModel> findByRoomId(UUID roomId, Pageable pageable);

    long countByRoomIdAndStatus(UUID roomId, MessageStatusType status);

}
