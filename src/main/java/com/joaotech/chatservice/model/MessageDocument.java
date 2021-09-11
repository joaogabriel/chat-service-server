package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDocument {

    private String id;
    private String token = TokenGenerator.getNew();
    public String roomToken;
    public String content;
    public LocalDateTime timestamp;
    public MessageStatus status;
    public MessageType type;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
