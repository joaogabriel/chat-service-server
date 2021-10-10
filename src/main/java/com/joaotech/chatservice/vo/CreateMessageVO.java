package com.joaotech.chatservice.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joaotech.chatservice.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageVO {

    public String messageId;
    public String roomId;
    @JsonProperty("userToken")
    public String messageOwnerToken;
    public String content;
    public Long timestamp;
    public MessageType type;

}
