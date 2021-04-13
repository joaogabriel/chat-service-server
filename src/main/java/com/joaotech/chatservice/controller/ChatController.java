package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.ChatMessageService;
import com.joaotech.chatservice.vo.ChatMessageVO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageVO chatMessage) {
        chatMessageService.save(chatMessage);
    }

}
