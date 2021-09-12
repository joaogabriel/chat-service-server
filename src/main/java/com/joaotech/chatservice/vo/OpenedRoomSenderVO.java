package com.joaotech.chatservice.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class OpenedRoomSenderVO {

    public String token;
    public LocalDateTime startedOn;
    public UserVO recipient;
    public UserVO sender;

}
