package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Document
public class RoomDocument {

    @Id
    private String id;
    private String token = TokenGenerator.getNew();
    public LocalDateTime startedOn;
    public LocalDateTime closedOn;
    public UserDocument sender;
    public UserDocument recipient;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
