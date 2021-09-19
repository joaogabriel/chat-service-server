package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageModel, String> {

    List<MessageModel> findAllByRoomToken(String token);

    MessageModel findByToken(String token);

    long countByRoomTokenAndStatus(String roomToken, MessageStatus status);

}
