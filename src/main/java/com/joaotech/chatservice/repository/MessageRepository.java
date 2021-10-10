package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<MessageModel, UUID> {

    Slice<MessageModel> findByRoomId(UUID roomId, Pageable pageable);

    long countByRoomId(UUID roomId);

    long countByRoomIdAndStatus(UUID roomId, String status);

}
