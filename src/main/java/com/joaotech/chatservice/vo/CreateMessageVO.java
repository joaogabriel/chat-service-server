package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageVO {

    public String roomToken;
    public String userToken;
    public String content;
    public MessageType type;

}
