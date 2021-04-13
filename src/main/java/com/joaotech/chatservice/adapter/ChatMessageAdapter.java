package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.vo.ChatMessageVO;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessageAdapter {

    public static ChatMessageVO toChatMessageVO(ChatMessageDocument chatMessageDocument) {
        return ChatMessageVO.builder()
                .token(chatMessageDocument.getToken())
                .roomToken(chatMessageDocument.roomToken)
                .content(chatMessageDocument.content)
                .timestamp(chatMessageDocument.timestamp)
                .status(chatMessageDocument.status)
                .type(chatMessageDocument.type)
                .build();
    }

    public static List<ChatMessageVO> toChatMessageVO(List<ChatMessageDocument> chatMessageDocuments) {
        return chatMessageDocuments.stream()
                .map(ChatMessageAdapter::toChatMessageVO)
                .collect(Collectors.toList());
    }

}
