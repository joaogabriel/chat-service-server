package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomVO;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toChatRoomVO(RoomDocument roomDocument) {
        if (roomDocument == null) return null;
        return RoomVO.builder()
                .id(roomDocument.getId().toString())
                .startedOn(roomDocument.startedOn)
                .closedOn(roomDocument.closedOn)
                .sender(new UserVO(roomDocument.senderToken, roomDocument.senderName))
                .recipient(new UserVO(roomDocument.recipientToken, roomDocument.recipientName))
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<RoomDocument> roomDocuments) {
        return roomDocuments.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

    public static OpenedRoomSenderVO toOpenedRoomSenderVO(RoomDocument roomDocument) {
        return OpenedRoomSenderVO.builder()
                .id(roomDocument.getId().toString())
                .startedOn(roomDocument.startedOn)
                .sender(new UserVO(roomDocument.senderToken, roomDocument.senderName))
                .recipient(new UserVO(roomDocument.recipientToken, roomDocument.recipientName))
                .build();
    }

    public static List<OpenedRoomSenderVO> toOpenedRoomSenderVO(List<RoomDocument> roomDocumentDocuments) {
        return roomDocumentDocuments.stream()
                .map(RoomAdapter::toOpenedRoomSenderVO)
                .collect(Collectors.toList());
    }

}
