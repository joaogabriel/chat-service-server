package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.vo.RoomVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toChatRoomVO(RoomDocument roomDocument) {
        return RoomVO.builder()
                .token(roomDocument.getToken())
                .startedOn(roomDocument.startedOn)
                .closedOn(roomDocument.closedOn)
                .sender(UserAdapter.toChatUserVO(roomDocument.sender))
                .recipient(UserAdapter.toChatUserVO(roomDocument.recipient))
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<RoomDocument> roomDocuments) {
        return roomDocuments.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

}
