package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageModel, String> {

    @Query("SELECT * FROM message WHERE room_id=?0 ALLOW FILTERING")
    List<MessageModel> findAllByRoom(String id);

    long countByRoomIdAndStatus(String roomId, MessageStatus status);

}
