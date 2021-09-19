package com.joaotech.chatservice.repository;

import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, String> {

    Optional<RoomModel> findByToken(String token);

}
