package com.joaotech.chatservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomContentVO {

    public RoomVO room;
    public long quantityOfMessages;

}
