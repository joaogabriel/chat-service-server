package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomVO;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toChatRoomVO(Room room) {
        return RoomVO.builder()
                .token(room.getToken())
                .startedOn(room.startedOn)
                .closedOn(room.closedOn)
                .sender(UserVO.builder().token(room.senderToken).build())
                .recipient(UserVO.builder().token(room.recipientToken).build())
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<Room> rooms) {
        return rooms.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

    public static OpenedRoomSenderVO toOpenedRoomSenderVO(Room roomDocument) {
        return OpenedRoomSenderVO.builder()
                .token(roomDocument.getToken())
                .startedOn(roomDocument.startedOn)
                .recipient(UserVO.builder().token(roomDocument.recipientToken).build())
                .build();
    }

    public static List<OpenedRoomSenderVO> toOpenedRoomSenderVO(List<Room> roomDocuments) {
        return roomDocuments.stream()
                .map(RoomAdapter::toOpenedRoomSenderVO)
                .collect(Collectors.toList());
    }

}
