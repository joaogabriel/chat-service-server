package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class MessageStatusVO {

    public MessageStatus status;
    public Long timestamp;

}
