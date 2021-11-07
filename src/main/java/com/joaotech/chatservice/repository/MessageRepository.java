package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MessageRepository extends CrudRepository<MessageDocument, UUID> {

    Slice<MessageDocument> findByRoomId(UUID roomId, Pageable pageable);

    long countByRoomId(UUID roomId);

    long countByRoomIdAndStatus(UUID roomId, String status);

}
