package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Document
public class ChatMessageDocument {

    @Id
    private String id;
    private String token = TokenGenerator.getNew();
    public String roomToken;
    public String content;
    public LocalDateTime timestamp;
    public MessageStatus status;
    public MessageType type;

}
