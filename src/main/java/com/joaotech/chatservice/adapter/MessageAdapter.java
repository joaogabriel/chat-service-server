package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.vo.MessageVO;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAdapter {

    public static MessageVO toChatMessageVO(MessageDocument messageDocument) {
        return MessageVO.builder()
                .token(messageDocument.getToken())
                .roomToken(messageDocument.roomToken)
                .content(messageDocument.content)
                .timestamp(messageDocument.timestamp)
                .status(messageDocument.status)
                .type(messageDocument.type)
                .build();
    }

    public static List<MessageVO> toChatMessageVO(List<MessageDocument> messageDocuments) {
        return messageDocuments.stream()
                .map(MessageAdapter::toChatMessageVO)
                .collect(Collectors.toList());
    }

}
