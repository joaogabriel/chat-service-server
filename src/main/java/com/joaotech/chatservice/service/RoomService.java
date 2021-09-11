package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.RoomAdapter;
import com.joaotech.chatservice.dao.RoomDAO;
import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.model.UserDocument;
import com.joaotech.chatservice.util.TokenGenerator;
import com.joaotech.chatservice.vo.OpenRoomVO;
import com.joaotech.chatservice.vo.RoomContentVO;
import com.joaotech.chatservice.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RoomService {

    public String open(OpenRoomVO room) {

        UserVO sender = room.sender;

        UserVO recipient = room.recipient;

        RoomDAO roomDAO = null;
        try {
            roomDAO = new RoomDAO();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//        Room previousOpenedRoom = roomDAO.findBySender(room.recipient.token);

//        if (previousOpenedRoom.isPresent()) {
//            throw new RuntimeException();
//        }

        UserDocument senderDocument = UserDocument.builder()
                .token(sender.token)
                .name(sender.name)
//                .color(sender.color)
                .build();

        UserDocument recipientDocument = UserDocument.builder()
                .token(recipient.token)
                .name(recipient.name)
//                .color(recipient.color)
                .build();

        Room roomDocument = Room.builder()
                .sender(senderDocument.token)
                .recipient(recipientDocument.token)
                .startedOn(LocalDateTime.now())
                .token(TokenGenerator.getNew())
                .build();

        roomDAO.insert(roomDocument);

        return roomDocument.getToken();

    }

    public void close(String token) {

        RoomDAO roomDAO = null;
        try {
            roomDAO = new RoomDAO();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Room room = roomDAO.findBySender(token);

        room.closedOn = LocalDateTime.now();

        roomDAO.insert(room);

    }

    public Room findByToken(String token) {

        RoomDAO roomDAO = null;
        try {
            roomDAO = new RoomDAO();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return roomDAO.findBySender(token);
    }

    public RoomContentVO getContent(String token) {

        RoomDAO roomDAO = null;
        try {
            roomDAO = new RoomDAO();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        roomDAO.findBySender(token);

//        List<MessageVO> messages = messageService.findByRoom(token);

        return RoomContentVO.builder()
                .room(RoomAdapter.toChatRoomVO(roomDAO.findBySender(token)))
//                .messages(messages)
                .build();

    }

}
