package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.ChatRoomDocument;
import com.joaotech.chatservice.model.ChatUserDocument;
import com.joaotech.chatservice.repository.ChatRoomRepository;
import com.joaotech.chatservice.vo.ChatRoomVO;
import com.joaotech.chatservice.vo.ChatUserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatMessageService chatMessageService;

    private final ChatRoomRepository chatRoomRepository;

    public void open(ChatRoomVO room) {

        ChatUserVO sender = room.sender;

        ChatUserVO recipient = room.recipient;

        Optional<ChatRoomDocument> previousOpenedRoom = chatRoomRepository.findBySenderTokenAndRecipientTokenAndClosedOnIsNull(sender.token, recipient.token);

        if (previousOpenedRoom.isPresent()) {
            throw new RuntimeException();
        }

        ChatUserDocument senderDocument = ChatUserDocument.builder()
                .token(sender.token)
                .name(sender.name)
                .color(sender.color)
                .build();

        ChatUserDocument recipientDocument = ChatUserDocument.builder()
                .token(recipient.token)
                .name(recipient.name)
                .color(recipient.color)
                .build();

        ChatRoomDocument roomDocument = ChatRoomDocument.builder()
                .sender(senderDocument)
                .recipient(recipientDocument)
                .startedOn(LocalDateTime.now())
                .build();

        chatRoomRepository.save(roomDocument);

    }

    public void close(ChatRoomVO room) {

        ChatUserVO sender = room.sender;

        ChatUserVO recipient = room.recipient;

        Optional<ChatRoomDocument> previousOpenedRoom = chatRoomRepository.findBySenderTokenAndRecipientTokenAndClosedOnIsNull(sender.token, recipient.token);

        if (previousOpenedRoom.isEmpty()) {
            throw new RuntimeException();
        }

        ChatRoomDocument roomDocument = previousOpenedRoom.get();

        roomDocument.closedOn = LocalDateTime.now();

        chatRoomRepository.save(roomDocument);

    }

    public void getContent(String token) {

        ChatRoomDocument roomDocument = chatRoomRepository.findByToken(token).orElseThrow(RuntimeException::new);




    }

}
