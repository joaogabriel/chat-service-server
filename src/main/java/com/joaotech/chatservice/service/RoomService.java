package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.RoomAdapter;
import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.repository.RoomRepository;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.OpenedRoomSenderVO;
import com.joaotech.chatservice.vo.RoomContentVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomService {

    private final NotificationService notificationService;

    private final RoomRepository roomRepository;

    private final MessageRepository messageRepository;

    @Transactional
    public String open(OpenRoomVO room) {

        UserVO sender = room.sender;

        UserVO recipient = room.recipient;

        Optional<RoomDocument> previousOpenedRoom = roomRepository.findBySenderTokenAndRecipientTokenAndIsClosedIsFalse(sender.token, recipient.token);

        if (previousOpenedRoom.isPresent()) {
            throw new RuntimeException();
        }

        RoomDocument roomDocument = RoomDocument.builder()
                .id(UUID.randomUUID())
                .senderToken(sender.token)
                .senderName(sender.name)
                .recipientToken(recipient.token)
                .recipientName(recipient.name)
                .startedOn(LocalDateTime.now())
                .build();

        roomRepository.save(roomDocument);

        return roomDocument.getId().toString();

    }

    public void close(String token) {

        RoomDocument roomDocument = roomRepository.findById(UUID.fromString(token)).orElseThrow(RuntimeException::new);

        roomDocument.closedOn = LocalDateTime.now();

        roomDocument.isClosed = true;

        roomRepository.save(roomDocument);

        notificationService.notifyRoom(roomDocument);

    }

    public RoomDocument findById(String token) {
        return roomRepository.findById(UUID.fromString(token)).orElseThrow(RuntimeException::new);
    }

    public RoomContentVO getContent(String id) {

        if (StringUtils.isEmpty(id)) throw new RuntimeException("room id is required");

        UUID roomId = UUID.fromString(id);

        RoomDocument room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);

        // TODO: 10/10/21 quando mover para definitivo, remover dependencia do messageRepository daqui
        long quantityOfMessages = messageRepository.countByRoomId(roomId);

        return RoomContentVO.builder()
                .room(RoomAdapter.toChatRoomVO(room))
                .quantityOfMessages(quantityOfMessages)
                .build();

    }

    public List<OpenedRoomSenderVO> getOpenedUserRooms(String userToken) {

        List<RoomDocument> roomDocuments = roomRepository.findByRecipientTokenAndIsClosedIsFalse(userToken);

        roomDocuments.addAll(roomRepository.findBySenderTokenAndIsClosedIsFalse(userToken));

        return RoomAdapter.toOpenedRoomSenderVO(roomDocuments);

    }

}
