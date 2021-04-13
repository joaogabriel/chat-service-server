package com.joaotech.chatservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomContentVO {

    public ChatRoomVO room;
    public List<ChatMessageVO> messages;

}
