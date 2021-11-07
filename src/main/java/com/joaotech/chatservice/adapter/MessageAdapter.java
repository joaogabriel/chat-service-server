package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.vo.MessageVO;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAdapter {

    public static MessageVO toChatMessageVO(MessageDocument messageDocument) {
        if (messageDocument == null) return null;
        return MessageVO.builder()
                .id(messageDocument.getId().toString())
                .roomId(messageDocument.roomId.toString())
                .content(messageDocument.content)
                .timestamp(messageDocument.timestamp)
                .currentStatus(messageDocument.currentStatus)
                .type(messageDocument.type)
                .userToken(messageDocument.messageOwnerToken)
                .build();
    }

    public static List<MessageVO> toChatMessageVO(List<MessageDocument> messageDocuments) {
        return messageDocuments.stream()
                .map(MessageAdapter::toChatMessageVO)
                .collect(Collectors.toList());
    }

}
