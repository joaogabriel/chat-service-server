package com.joaotech.chatservice.vo;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class OpenedRoomSenderVO {

    public UUID id;
    public LocalDateTime startedOn;
    public UserVO recipient;
    public UserVO sender;

}
