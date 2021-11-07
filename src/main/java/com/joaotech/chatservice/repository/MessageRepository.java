package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.MessageModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//CassandraRepository
//PagingAndSortingRepository
//CrudRepository
public interface MessageRepository extends CrudRepository<MessageModel, UUID> {

    Slice<MessageModel> findByRoomId(UUID roomId, Pageable pageable);

//    List<MessageModel> findByRoomId(UUID roomId);

    long countByRoomId(UUID roomId);

    long countByRoomIdAndStatus(UUID roomId, String status);

}
