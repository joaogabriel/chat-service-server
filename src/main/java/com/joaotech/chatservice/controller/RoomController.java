package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.RoomService;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.RoomContentVO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/open")
    public ResponseEntity<String> open(@RequestBody OpenRoomVO room) {
        String roomToken = roomService.open(room);
        return ResponseEntity.ok(roomToken);
    }

    @PostMapping("/{token}/close")
    public ResponseEntity<?> close(@PathVariable String token) {
        roomService.close(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{token}/content")
    public ResponseEntity<RoomContentVO> getContent(@PathVariable String token) {
        RoomContentVO content = roomService.getContent(token);
        return ResponseEntity.ok(content);
    }

}
