package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.model.ChatNotificationDocument;
import com.joaotech.chatservice.service.ChatMessageService;
import com.joaotech.chatservice.service.ChatRoomService;
import com.joaotech.chatservice.vo.ChatMessageVO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageVO chatMessage) {
        chatMessageService.save(chatMessage);

//        messagingTemplate.convertAndSendToUser(
//                chatMessageDocument.getRecipientId(), "/queue/messages",
//                new ChatNotificationDocument(
//                        saved.getId(),
//                        saved.getSenderId(),
//                        saved.getSenderName()));
    }

}
