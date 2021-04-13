package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.ChatRoomDocument;
import com.joaotech.chatservice.vo.ChatRoomVO;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomAdapter {

    public static ChatRoomVO toChatRoomVO(ChatRoomDocument chatRoomDocument) {
        return ChatRoomVO.builder()
                .token(chatRoomDocument.getToken())
                .startedOn(chatRoomDocument.startedOn)
                .closedOn(chatRoomDocument.closedOn)
                .sender(ChatUserAdapter.toChatUserVO(chatRoomDocument.sender))
                .recipient(ChatUserAdapter.toChatUserVO(chatRoomDocument.recipient))
                .build();
    }

    public static List<ChatRoomVO> toChatRoomVO(List<ChatRoomDocument> chatRoomDocuments) {
        return chatRoomDocuments.stream()
                .map(ChatRoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

}
