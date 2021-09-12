package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.RoomAdapter;
import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.model.User;
import com.joaotech.chatservice.reposistory.RoomRepository;
import com.joaotech.chatservice.util.TokenGenerator;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.RoomContentVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public String open(OpenRoomVO room) {

        UserVO sender = room.sender;

        UserVO recipient = room.recipient;

//        Room previousOpenedRoom = roomDAO.findBySender(room.recipient.token);

//        if (previousOpenedRoom.isPresent()) {
//            throw new RuntimeException();
//        }

        User senderDocument = User.builder()
                .token(sender.token)
                .name(sender.name)
//                .color(sender.color)
                .build();

        User recipientDocument = User.builder()
                .token(recipient.token)
                .name(recipient.name)
//                .color(recipient.color)
                .build();

        Room roomDocument = Room.builder()
                .sender(senderDocument.token)
                .recipient(recipientDocument.token)
                .startedOn(LocalDateTime.now())
                .token(TokenGenerator.getNew())
                .id(TokenGenerator.getNew())
                .build();

        roomRepository.save(roomDocument);

        return roomDocument.getToken();

    }

    public void close(String token) {

//        RoomDAO roomDAO = null;
//        try {
//            roomDAO = new RoomDAO();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        Room room = roomRepository.findById(token).orElse(null);

        room.closedOn = LocalDateTime.now();

        roomRepository.save(room);

    }

    public Room findByToken(String token) {

        //        TODO ECLUIR PRA UTILIZAR O REPOSITORY
//        RoomDAO roomDAO = null;
//        try {
//            roomDAO = new RoomDAO();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        return roomRepository.findById(token).orElse(null);
    }

    public RoomContentVO getContent(String token) {

        Optional<Room> room = roomRepository.findByToken(token);

        return RoomContentVO.builder()
                .room(RoomAdapter.toChatRoomVO(room.orElse(null)))
                .build();

    }

}
