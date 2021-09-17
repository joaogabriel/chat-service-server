package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.vo.MessageVO;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAdapter {

    public static MessageVO toChatMessageVO(MessageModel messageModel) {
        return MessageVO.builder()
                .token(messageModel.getToken())
                .roomToken(messageModel.roomToken)
                .content(messageModel.content)
                .timestamp(messageModel.timestamp)
                .status(messageModel.status)
                .type(messageModel.type)
                .build();
    }

    public static List<MessageVO> toChatMessageVO(List<MessageModel> messageModels) {
        return messageModels.stream()
                .map(MessageAdapter::toChatMessageVO)
                .collect(Collectors.toList());
    }

}
