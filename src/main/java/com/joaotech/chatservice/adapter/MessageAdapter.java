package com.joaotech.chatservice.adapter;

import com.datastax.oss.driver.api.core.cql.Row;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.PaginatedMessagesVO;

import java.util.List;

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

//    public static PaginatedMessagesVO toPaginatedMessagesVO(List<MessageModel> messages, String cursorMark) {
//
//        List<MessageVO> messagesVO = messages.stream()
//                .map(MessageAdapter::toChatMessageVO)
//                .collect(Collectors.toList());
//
//        return PaginatedMessagesVO.builder()
//                .messages(messagesVO)
//                .cursorMark(cursorMark)
//                .build();
//
//    }

    public static PaginatedMessagesVO toPaginatedMessagesVO(List<MessageVO> messages, int count, String cursorMark) {

        return PaginatedMessagesVO.builder()
                .messages(messages)
                .count(count)
                .cursorMark(cursorMark)
                .build();

    }

    public static MessageVO toMessageVO(Row row) {

        return MessageVO.builder()
                .id((row.getUuid("id").toString()))
                .roomId((row.getUuid("room_id").toString()))
//                .userToken((row.getString("user_token")))
                .content((row.getString("content")))
                .currentStatus((row.getString("current_status")))
//                .timestamp((row.getString("id")))
//                .type((row.getString("id")))
                .build();

    }

}
