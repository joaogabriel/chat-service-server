package com.joaotech.chatservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class RoomTokenVO {

    public String token;

}
