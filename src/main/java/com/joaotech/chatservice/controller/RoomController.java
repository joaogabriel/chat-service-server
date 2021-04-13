package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.ChatMessageService;
import com.joaotech.chatservice.service.ChatRoomService;
import com.joaotech.chatservice.vo.ChatRoomVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RoomController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    @MessageMapping("/room/open")
    public void open(@Payload ChatRoomVO room) {
        chatRoomService.open(room);
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
