package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomVO;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toChatRoomVO(RoomModel roomModel) {
        if (roomModel == null) return null;
        return RoomVO.builder()
                .id(roomModel.getId().toString())
                .startedOn(roomModel.startedOn)
                .closedOn(roomModel.closedOn)
                .sender(new UserVO(roomModel.senderToken, roomModel.senderName))
                .recipient(new UserVO(roomModel.recipientToken, roomModel.recipientName))
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<RoomModel> roomModels) {
        return roomModels.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

    public static OpenedRoomSenderVO toOpenedRoomSenderVO(RoomModel roomModel) {
        return OpenedRoomSenderVO.builder()
                .id(roomModel.getId())
                .startedOn(roomModel.startedOn)
                .sender(new UserVO(roomModel.senderToken, roomModel.senderName))
                .recipient(new UserVO(roomModel.recipientToken, roomModel.recipientName))
                .build();
    }

    public static List<OpenedRoomSenderVO> toOpenedRoomSenderVO(List<RoomModel> roomModelDocuments) {
        return roomModelDocuments.stream()
                .map(RoomAdapter::toOpenedRoomSenderVO)
                .collect(Collectors.toList());
    }

}
