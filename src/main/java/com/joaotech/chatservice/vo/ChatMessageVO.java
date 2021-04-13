package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.MessageType;
import com.joaotech.chatservice.util.TokenGenerator;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
public class ChatMessageVO {

    public String token;
    public String roomToken;
    public String content;
    public Date timestamp;
    public MessageStatus status;
    public MessageType type;

}
