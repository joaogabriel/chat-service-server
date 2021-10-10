package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.MessageService;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload CreateMessageVO chatMessage) {
        chatMessage.roomId = "cc47f515-023c-43c8-84f2-251edf4eb0b3";
        chatMessage.messageId = UUID.randomUUID().toString();
        chatMessage.timestamp = 1633878887L;
        messageService.save(chatMessage);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageVO> getContent(@PathVariable String id) {
        MessageVO message = messageService.findById(id);
        return ResponseEntity.ok(message);
    }

}
