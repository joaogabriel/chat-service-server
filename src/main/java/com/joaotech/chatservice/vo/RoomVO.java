package com.joaotech.chatservice.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RoomVO {

    public String id;
    public LocalDateTime startedOn;
    public LocalDateTime closedOn;
    public UserVO sender;
    public UserVO recipient;

}
