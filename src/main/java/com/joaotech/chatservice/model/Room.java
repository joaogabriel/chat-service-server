package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private String id;
    private String token = TokenGenerator.getNew();
    public LocalDateTime startedOn;
    public LocalDateTime closedOn;
    public String sender;
    public String recipient;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
