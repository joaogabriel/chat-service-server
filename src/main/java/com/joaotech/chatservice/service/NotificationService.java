package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.vo.RoomNotificationVO;
import com.joaotech.chatservice.vo.RoomsNotificationVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationService {

    private static final String MESSAGE_DESTINATION = "/queue/rooms";

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyInvolved(RoomModel roomModel, MessageModel messageModel) {

        notifyRoom(roomModel);

        notifyRooms(roomModel, messageModel);

    }

    public void notifyRoom(RoomModel roomModel) {

        RoomsNotificationVO roomsNotificationVO = RoomsNotificationVO.builder()
                .id(roomModel.getId().toString())
                .sender(
                        UserVO.builder()
                                .token(roomModel.senderToken)
                                .name(roomModel.senderName)
                                .build()
                )
                .recipient(
                        UserVO.builder()
                                .token(roomModel.recipientToken)
                                .name(roomModel.recipientName)
                                .build()
                )
                .build();

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION, roomsNotificationVO);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION, roomsNotificationVO);

    }

    public void notifyRooms(RoomModel roomModel, MessageModel messageModel) {

        RoomNotificationVO roomsNotificationVO = RoomNotificationVO.builder()
                .messageId(messageModel.getId().toString())
                .sender(UserVO.builder().token(roomModel.senderToken).build())
                .recipient(UserVO.builder().token(roomModel.recipientToken).build())
                .build();

        Map<String, Object> headers = produceHeaders(messageModel);

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO, headers);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO, headers);

    }

    private Map<String, Object> produceHeaders(MessageModel messageModel) {
        return Map.of(StompHeaderAccessor.STOMP_MESSAGE_ID_HEADER, messageModel.getId());
    }

}
