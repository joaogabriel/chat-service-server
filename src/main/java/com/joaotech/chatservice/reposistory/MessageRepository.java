package com.joaotech.chatservice.reposistory;

import com.joaotech.chatservice.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, String> {

    List<Message> findAllByToken(String token);

}
