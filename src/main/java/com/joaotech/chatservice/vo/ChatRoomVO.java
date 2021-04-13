package com.joaotech.chatservice.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ChatRoomVO {

    public String token;
    public LocalDateTime startedOn;
    public LocalDateTime closedOn;
    public ChatUserVO sender;
    public ChatUserVO recipient;

}
