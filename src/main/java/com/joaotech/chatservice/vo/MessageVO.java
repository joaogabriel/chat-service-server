package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {

    public String id;
    public String roomId;
    public String userToken;
    public String content;
    public String currentStatus;
    public LocalDateTime timestamp;
    public MessageType type;

}
