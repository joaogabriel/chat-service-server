package com.joaotech.chatservice.controller;

import com.joaotech.chatservice.service.MessageService;
import com.joaotech.chatservice.service.RoomService;
import com.joaotech.chatservice.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final MessageService messageService;

    @PostMapping("/open")
    public ResponseEntity<RoomIdVO> open(@RequestBody OpenRoomVO room) {
        String id = roomService.open(room);
        return ResponseEntity.ok(new RoomIdVO(id));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<?> close(@PathVariable String id) {
        roomService.close(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomContentVO> findById(@PathVariable String id) {
        RoomContentVO content = roomService.getContent(id);
        return ResponseEntity.ok(content);
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<PaginatedMessagesVO> findMessages(@PathVariable String id, @RequestParam(required = false) String cursorMark) {
        PaginatedMessagesVO paginatedMessagesVO = messageService.findByRoom(id, cursorMark);
        return ResponseEntity.ok(paginatedMessagesVO);
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<List<OpenedRoomSenderVO>> getOpenedUserRooms(@PathVariable String userid) {
        List<OpenedRoomSenderVO> rooms = roomService.getOpenedUserRooms(userid);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}/new-messages-count")
    public ResponseEntity<Long> countNewMessages(@PathVariable String id) {
        Long countNewMessages = messageService.countNewMessages(id);
        return ResponseEntity.ok(countNewMessages);
    }

}
