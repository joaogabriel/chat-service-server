package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.model.ChatNotificationDocument;
import com.joaotech.chatservice.service.ChatMessageService;
import com.joaotech.chatservice.service.ChatRoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService, ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDocument chatMessageDocument) {
        var chatId = chatRoomService
                .getChatId(chatMessageDocument.getSenderId(), chatMessageDocument.getRecipientId(), true);
        chatMessageDocument.setChatId(chatId.get());

        ChatMessageDocument saved = chatMessageService.save(chatMessageDocument);

        messagingTemplate.convertAndSendToUser(
                chatMessageDocument.getRecipientId(), "/queue/messages",
                new ChatNotificationDocument(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }

}
