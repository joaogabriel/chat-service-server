package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.ChatRoomService;
import com.joaotech.chatservice.vo.ChatRoomContentVO;
import com.joaotech.chatservice.vo.ChatRoomVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/open")
    public ResponseEntity<String> open(@Payload ChatRoomVO room) {
        String roomToken = chatRoomService.open(room);
        return ResponseEntity.ok(roomToken);
    }

    @PostMapping("/close")
    public void close(@Payload ChatRoomVO room) {
        chatRoomService.close(room);
    }

    @GetMapping("/{token}/content")
    public ResponseEntity<ChatRoomContentVO> getContent(@PathVariable String token) {
        ChatRoomContentVO content = chatRoomService.getContent(token);
        return ResponseEntity.ok(content);
    }

}
