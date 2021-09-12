package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.Message;
import com.joaotech.chatservice.model.MessageStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {

    List<Message> findAllByRoomToken(String token);

    Message findByToken(String token);

    long countByRoomTokenAndStatus(String roomToken, MessageStatus status);

}
