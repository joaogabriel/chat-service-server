package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.ChatMessageService;
import com.joaotech.chatservice.service.ChatRoomService;
import com.joaotech.chatservice.vo.ChatRoomVO;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    public RoomController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService, ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/room/open")
    public void open(@Payload ChatRoomVO room) {

        chatRoomService.open(room);

//        messagingTemplate.convertAndSendToUser(
//                chatMessageDocument.getRecipientId(), "/queue/messages",
//                new ChatNotificationDocument(
//                        saved.getId(),
//                        saved.getSenderId(),
//                        saved.getSenderName()));

    }

    @MessageMapping("/room/close")
    public void close(@Payload ChatRoomVO room) {
        chatRoomService.close(room);
    }

    @GetMapping("/room/{token}")
    public ResponseEntity<Long> getContent(@PathVariable String token) {
        return ResponseEntity.ok(chatMessageService.countNewMessages(token, token));
    }

}
