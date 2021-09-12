package com.joaotech.chatservice.vo;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class RoomVO {

    public String token;
    public LocalDateTime startedOn;
    public LocalDateTime closedOn;
    public UserVO sender;
    public UserVO recipient;

}
