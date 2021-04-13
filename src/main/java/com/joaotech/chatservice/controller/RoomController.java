package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.RoomService;
import com.joaotech.chatservice.vo.RoomContentVO;
import com.joaotech.chatservice.vo.RoomVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/open")
    public ResponseEntity<String> open(@Payload RoomVO room) {
        String roomToken = roomService.open(room);
        return ResponseEntity.ok(roomToken);
    }

    @PostMapping("/close")
    public void close(@Payload RoomVO room) {
        roomService.close(room);
    }

    @GetMapping("/{token}/content")
    public ResponseEntity<RoomContentVO> getContent(@PathVariable String token) {
        RoomContentVO content = roomService.getContent(token);
        return ResponseEntity.ok(content);
    }

}
