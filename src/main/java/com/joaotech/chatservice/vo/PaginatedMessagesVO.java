package com.joaotech.chatservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedMessagesVO {

    public List<MessageVO> messages;
    public String cursorMark;

}
