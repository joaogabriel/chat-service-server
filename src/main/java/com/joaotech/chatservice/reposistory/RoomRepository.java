package com.joaotech.chatservice.reposistory;

import com.joaotech.chatservice.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findByToken(String token);

}
