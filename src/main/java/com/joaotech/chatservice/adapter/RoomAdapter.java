package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toRoomVO(RoomDocument roomDocument) {
        return RoomVO.builder()
                .token(roomDocument.getToken())
                .startedOn(roomDocument.startedOn)
                .closedOn(roomDocument.closedOn)
                .sender(UserAdapter.toUserVO(roomDocument.sender))
                .recipient(UserAdapter.toUserVO(roomDocument.recipient))
                .build();
    }

    public static List<RoomVO> toRoomVO(List<RoomDocument> roomDocuments) {
        return roomDocuments.stream()
                .map(RoomAdapter::toRoomVO)
                .collect(Collectors.toList());
    }

    public static OpenedRoomSenderVO toOpenedRoomSenderVO(RoomDocument roomDocument) {
        return OpenedRoomSenderVO.builder()
                .token(roomDocument.getToken())
                .startedOn(roomDocument.startedOn)
                .recipient(UserAdapter.toUserVO(roomDocument.recipient))
                .build();
    }

    public static List<OpenedRoomSenderVO> toOpenedRoomSenderVO(List<RoomDocument> roomDocuments) {
        return roomDocuments.stream()
                .map(RoomAdapter::toOpenedRoomSenderVO)
                .collect(Collectors.toList());
    }

}
