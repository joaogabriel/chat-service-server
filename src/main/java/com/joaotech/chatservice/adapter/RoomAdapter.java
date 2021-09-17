package com.joaotech.chatservice.adapter;

import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomVO;
import com.joaotech.chatservice.vo.UserVO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomAdapter {

    public static RoomVO toChatRoomVO(RoomModel roomModel) {
        return RoomVO.builder()
                .token(roomModel.getToken())
                .startedOn(roomModel.startedOn)
                .closedOn(roomModel.closedOn)
                .sender(UserVO.builder().token(roomModel.senderToken).build())
                .recipient(UserVO.builder().token(roomModel.recipientToken).build())
                .build();
    }

    public static List<RoomVO> toChatRoomVO(List<RoomModel> roomModels) {
        return roomModels.stream()
                .map(RoomAdapter::toChatRoomVO)
                .collect(Collectors.toList());
    }

    public static OpenedRoomSenderVO toOpenedRoomSenderVO(RoomModel roomModelDocument) {
        return OpenedRoomSenderVO.builder()
                .token(roomModelDocument.getToken())
                .startedOn(roomModelDocument.startedOn)
                .recipient(UserVO.builder().token(roomModelDocument.recipientToken).build())
                .sender(UserVO.builder().token(roomModelDocument.senderToken).build())
                .build();
    }

    public static List<OpenedRoomSenderVO> toOpenedRoomSenderVO(List<RoomModel> roomModelDocuments) {
        return roomModelDocuments.stream()
                .map(RoomAdapter::toOpenedRoomSenderVO)
                .collect(Collectors.toList());
    }

}
