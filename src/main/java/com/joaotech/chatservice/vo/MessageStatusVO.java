package com.joaotech.chatservice.vo;

import com.joaotech.chatservice.model.MessageStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class MessageStatusVO {

    public MessageStatusType status;
    public Long timestamp;

}
