package com.joaotech.chatservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomNotificationVO {

    public String messageId;
    public UserVO sender;
    public UserVO recipient;

}
