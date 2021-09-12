package com.joaotech.chatservice.reposistory;

import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<Room> findByToken(String token);

}
