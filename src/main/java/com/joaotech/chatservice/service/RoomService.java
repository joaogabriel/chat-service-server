package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.RoomAdapter;
import com.joaotech.chatservice.model.RoomModel;
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

    public String open(OpenRoomVO room) {

        UserVO sender = room.sender;

        UserVO recipient = room.recipient;

        Optional<RoomModel> previousOpenedRoom = roomRepository.findBySenderTokenAndRecipientTokenAndClosedIsFalse(sender.token, recipient.token);

        if (previousOpenedRoom.isPresent()) {
            throw new RuntimeException();
        }

        RoomModel roomModel = RoomModel.builder()
                .id(UUID.randomUUID())
                .senderToken(sender.token)
                .senderName(sender.name)
                .recipientToken(recipient.token)
                .recipientName(recipient.name)
                .startedOn(LocalDateTime.now())
                .build();

        roomRepository.save(roomModel);

        return roomModel.getId().toString();

    }

    @Transactional
    public void close(String token) {

        RoomModel roomModel = roomRepository.findById(UUID.fromString(token)).orElseThrow(RuntimeException::new);

        roomModel.closedOn = LocalDateTime.now();

        roomModel.isClosed = true;

        roomRepository.save(roomModel);

        notificationService.notifyRoom(roomModel);

    }

    public RoomModel findById(String token) {
        return roomRepository.findById(UUID.fromString(token)).orElseThrow(RuntimeException::new);
    }

    public RoomContentVO getContent(String id) {

        if (StringUtils.isEmpty(id)) throw new RuntimeException("room id is required");

        UUID roomId = UUID.fromString(id);

        RoomModel room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);

        // TODO: 10/10/21 quando mover para definitivo, remover dependencia do messageRepository daqui
        long quantityOfMessages = messageRepository.countByRoomId(roomId);

        return RoomContentVO.builder()
                .room(RoomAdapter.toChatRoomVO(room))
                .quantityOfMessages(quantityOfMessages)
                .build();

    }

    public List<OpenedRoomSenderVO> getOpenedUserRooms(String userToken) {

        List<RoomModel> roomModels = roomRepository.findByRecipientTokenAndClosedIsFalse(userToken);

        roomModels.addAll(roomRepository.findBySenderTokenAndClosedIsFalse(userToken));

        return RoomAdapter.toOpenedRoomSenderVO(roomModels);

    }

}
