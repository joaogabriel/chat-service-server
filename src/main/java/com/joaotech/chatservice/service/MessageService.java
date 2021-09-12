package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.Message;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private static final String MESSAGE_DESTINATION = "/queue/rooms";

    private final SimpMessagingTemplate messagingTemplate;

    private final RoomService roomService;

    private final MessageRepository messageRepository;

    public void save(CreateMessageVO chatMessage) {

        Room room = roomService.findByToken(chatMessage.roomToken);

        if (room == null) {
            // TODO: 26/08/21 lancar excecao especifica
            throw new RuntimeException();
        }

        Message message = Message.builder()
                .roomToken(chatMessage.roomToken)
                .userToken(chatMessage.userToken)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.RECEIVED)
                .type(chatMessage.type)
                .build();

        messageRepository.save(message);

        notifyUsers(room, message);

    }

    private void notifyUsers(Room room, Message message) {

        UserNotificationVO chatNotification = new UserNotificationVO(message.getToken(), room.senderToken, room.senderToken);

        notifyRoom(room);

        notifyRooms(room, message);

        messagingTemplate.convertAndSendToUser(room.recipientToken, MESSAGE_DESTINATION, chatNotification);
    }

    private void notifyRoom(Room room) {

        RoomsNotificationVO roomsNotificationVO = RoomsNotificationVO.builder()
                .token(room.getToken())
                .sender(UserVO.builder().token(room.senderToken).build())
                .recipient(UserVO.builder().token(room.recipientToken).build())
                .build();

        messagingTemplate.convertAndSendToUser(room.recipientToken, MESSAGE_DESTINATION, roomsNotificationVO);

    }

    private void notifyRooms(Room room, Message message) {

        RoomNotificationVO roomsNotificationVO = RoomNotificationVO.builder()
                .messageToken(message.getToken())
                .sender(UserVO.builder().token(room.senderToken).build())
                .recipient(UserVO.builder().token(room.recipientToken).build())
                .build();

        messagingTemplate.convertAndSendToUser(room.recipientToken, MESSAGE_DESTINATION + "/" + room.getToken(), roomsNotificationVO);

    }

    public List<MessageVO> findByRoom(String roomToken) {

        List<Message> messages = messageRepository.findAllByRoomToken(roomToken);

        return MessageAdapter.toChatMessageVO(messages);

    }

    public MessageVO findByToken(String token) {

        Message message = messageRepository.findById(token).orElse(null);

        return MessageAdapter.toChatMessageVO(message);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomTokenAndStatus(roomToken, MessageStatus.RECEIVED);
    }

//    public List<ChatMessageDocument> findChatMessages(String senderId, String recipientId) {
//        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
//
//        List<ChatMessageDocument> messages = chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());
//
//        if (messages.size() > 0) {
//            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
//        }
//
//        return messages;
//    }
//
//    public ChatMessageDocument findById(String id) {
//        return repository
//                .findById(id)
//                .map(chatMessageDocument -> {
//                    chatMessageDocument.setStatus(MessageStatus.DELIVERED);
//                    return repository.save(chatMessageDocument);
//                })
//                .orElseThrow(() ->
//                        new RuntimeException("can't find message (" + id + ")")); // alterei aqui
//    }
//
//    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
//        Query query = new Query(
//                Criteria
//                        .where("senderId").is(senderId)
//                        .and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessageDocument.class);
//    }

}
