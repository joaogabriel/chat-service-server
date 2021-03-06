package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.vo.MessageVO;

public class MessageAdapter {

    public static MessageVO toChatMessageVO(MessageModel messageModel) {
        if (messageModel == null) return null;
        return MessageVO.builder()
                .id(messageModel.getId().toString())
                .roomId(messageModel.roomId.toString())
                .content(messageModel.content)
                .timestamp(messageModel.timestamp)
                .currentStatus(messageModel.currentStatus)
                .type(messageModel.type)
                .userToken(messageModel.messageOwnerToken)
                .build();
    }

}
