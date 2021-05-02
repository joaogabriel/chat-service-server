package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.RoomAdapter;
import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.model.UserDocument;
import com.joaotech.chatservice.repository.RoomRepository;
import com.joaotech.chatservice.util.TokenGenerator;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.RoomContentVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {

//    private final MessageService messageService;

    private final RoomRepository roomRepository;

    public String open(OpenRoomVO room) {

        UserVO sender = room.sender;

        UserVO recipient = room.recipient;

        Optional<RoomDocument> previousOpenedRoom = roomRepository.findBySender_TokenAndRecipient_TokenAndClosedOnIsNull(sender.token, recipient.token);

        if (previousOpenedRoom.isPresent()) {
            throw new RuntimeException();
        }

        UserDocument senderDocument = UserDocument.builder()
                .token(sender.token)
                .name(sender.name)
                .color(sender.color)
                .build();

        UserDocument recipientDocument = UserDocument.builder()
                .token(recipient.token)
                .name(recipient.name)
                .color(recipient.color)
                .build();

        RoomDocument roomDocument = RoomDocument.builder()
                .sender(senderDocument)
                .recipient(recipientDocument)
                .startedOn(LocalDateTime.now())
                .token(TokenGenerator.getNew())
                .build();

        roomRepository.save(roomDocument);

        return roomDocument.getToken();

    }

    public void close(String token) {

        RoomDocument roomDocument = roomRepository.findByToken(token).orElseThrow(RuntimeException::new);

        roomDocument.closedOn = LocalDateTime.now();

        roomRepository.save(roomDocument);

    }

    public RoomDocument findByToken(String token) {
        return roomRepository.findByToken(token).orElseThrow(RuntimeException::new);
    }

    public RoomContentVO getContent(String token) {

        RoomDocument roomDocument = roomRepository.findByToken(token).orElseThrow(RuntimeException::new);

//        List<MessageVO> messages = messageService.findByRoom(token);

        return RoomContentVO.builder()
                .room(RoomAdapter.toChatRoomVO(roomDocument))
//                .messages(messages)
                .build();

    }

}
