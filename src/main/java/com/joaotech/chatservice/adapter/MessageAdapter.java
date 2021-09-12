package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.Message;
import com.joaotech.chatservice.vo.MessageVO;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAdapter {

    public static MessageVO toChatMessageVO(Message message) {
        return MessageVO.builder()
                .token(message.getToken())
                .roomToken(message.roomToken)
                .content(message.content)
                .timestamp(message.timestamp)
                .status(message.status)
                .type(message.type)
                .build();
    }

    public static List<MessageVO> toChatMessageVO(List<Message> messages) {
        return messages.stream()
                .map(MessageAdapter::toChatMessageVO)
                .collect(Collectors.toList());
    }

}
