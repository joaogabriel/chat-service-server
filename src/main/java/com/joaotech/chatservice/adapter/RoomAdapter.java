package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.Room;
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
                .sender(UserVO.builder().token(room.sender).build())
                .recipient(UserVO.builder().token(room.recipient).build())
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<Room> rooms) {
        return rooms.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

}
