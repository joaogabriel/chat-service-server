package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Document
public class ChatMessageDocument {

    @Id
    private String id;
    private String token = TokenGenerator.getNew();
    public String roomToken;
    public String content;
    public Date timestamp;
    public MessageStatus status;
    public MessageType type;

}
