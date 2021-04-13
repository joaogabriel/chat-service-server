package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MessageVO {

    public String token;
    public String roomToken;
    public String content;
    public LocalDateTime timestamp;
    public MessageStatus status;
    public MessageType type;

}
