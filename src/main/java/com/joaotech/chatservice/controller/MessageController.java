package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.MessageService;
import com.joaotech.chatservice.vo.MessageVO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageVO chatMessage) {
        messageService.save(chatMessage);
    }

}
