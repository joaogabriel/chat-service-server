package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.ChatUserDocument;
import com.joaotech.chatservice.vo.ChatUserVO;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUserAdapter {

    public static ChatUserVO toChatUserVO(ChatUserDocument chatUserDocument) {
        return ChatUserVO.builder()
                .token(chatUserDocument.getToken())
                .name(chatUserDocument.name)
                .color(chatUserDocument.color)
                .build();
    }

    public static List<ChatUserVO> toChatUserVO(List<ChatUserDocument> chatUserDocuments) {
        return chatUserDocuments.stream()
                .map(ChatUserAdapter::toChatUserVO)
                .collect(Collectors.toList());
    }

}
