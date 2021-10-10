package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageVO {

    public String messageId;
    public String roomId;
    public String userToken;
    public String content;
    public Long timestamp;
    public MessageType type;

}
