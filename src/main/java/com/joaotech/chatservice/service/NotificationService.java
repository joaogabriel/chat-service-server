package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.model.RoomDocument;
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

    public void notifyInvolved(RoomDocument roomDocument, MessageDocument messageDocument) {

        notifyRoom(roomDocument);

        notifyRooms(roomDocument, messageDocument);

    }

    public void notifyRoom(RoomDocument roomDocument) {

        RoomsNotificationVO roomsNotificationVO = RoomsNotificationVO.builder()
                .id(roomDocument.getId().toString())
                .sender(
                        UserVO.builder()
                                .token(roomDocument.senderToken)
                                .name(roomDocument.senderName)
                                .build()
                )
                .recipient(
                        UserVO.builder()
                                .token(roomDocument.recipientToken)
                                .name(roomDocument.recipientName)
                                .build()
                )
                .build();

        messagingTemplate.convertAndSendToUser(roomDocument.recipientToken, MESSAGE_DESTINATION, roomsNotificationVO);

        messagingTemplate.convertAndSendToUser(roomDocument.senderToken, MESSAGE_DESTINATION, roomsNotificationVO);

    }

    public void notifyRooms(RoomDocument roomDocument, MessageDocument messageDocument) {

        RoomNotificationVO roomsNotificationVO = RoomNotificationVO.builder()
                .messageId(messageDocument.getId().toString())
                .sender(UserVO.builder().token(roomDocument.senderToken).build())
                .recipient(UserVO.builder().token(roomDocument.recipientToken).build())
                .build();

        Map<String, Object> headers = produceHeaders(messageDocument);

        messagingTemplate.convertAndSendToUser(roomDocument.recipientToken, MESSAGE_DESTINATION + "/" + roomDocument.getId(), roomsNotificationVO, headers);

        messagingTemplate.convertAndSendToUser(roomDocument.senderToken, MESSAGE_DESTINATION + "/" + roomDocument.getId(), roomsNotificationVO, headers);

    }

    private Map<String, Object> produceHeaders(MessageDocument messageDocument) {
        return Map.of(StompHeaderAccessor.STOMP_MESSAGE_ID_HEADER, messageDocument.getId());
    }

}
