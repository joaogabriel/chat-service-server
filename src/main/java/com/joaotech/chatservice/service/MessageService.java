package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.util.TokenGenerator;
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

        RoomModel roomModel = roomService.findByToken(chatMessage.roomToken);

        if (roomModel == null) {
            // TODO: 26/08/21 lancar excecao especifica
            throw new RuntimeException();
        }

        MessageModel messageModel = MessageModel.builder()
                .id(TokenGenerator.getNew())
                .roomId(chatMessage.roomToken)
                .userToken(chatMessage.userToken)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.RECEIVED.name())
                .type(chatMessage.type)
                .build();

        messageRepository.save(messageModel);

        notifyUsers(roomModel, messageModel);

    }

    private void notifyUsers(RoomModel roomModel, MessageModel messageModel) {

        UserNotificationVO chatNotification = new UserNotificationVO(messageModel.getId(), roomModel.recipientToken, roomModel.senderToken);

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION, chatNotification);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION, chatNotification);

        notifyRoom(roomModel);

        notifyRooms(roomModel, messageModel);

    }

    private void notifyRoom(RoomModel roomModel) {

        RoomsNotificationVO roomsNotificationVO = RoomsNotificationVO.builder()
                .token(roomModel.getId())
                .sender(UserVO.builder().token(roomModel.senderToken).build())
                .recipient(UserVO.builder().token(roomModel.recipientToken).build())
                .build();

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION, roomsNotificationVO);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION, roomsNotificationVO);

    }

    private void notifyRooms(RoomModel roomModel, MessageModel messageModel) {

        RoomNotificationVO roomsNotificationVO = RoomNotificationVO.builder()
                .messageToken(messageModel.getId())
                .sender(UserVO.builder().token(roomModel.senderToken).build())
                .recipient(UserVO.builder().token(roomModel.recipientToken).build())
                .build();

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO);

    }

    public List<MessageVO> findByRoom(String roomToken) {

        List<MessageModel> messageModels = messageRepository.findAllByRoom(roomToken);

        return MessageAdapter.toChatMessageVO(messageModels);

    }

    public MessageVO findByToken(String token) {

        MessageModel messageModel = messageRepository.findById(token).orElse(null);

        return MessageAdapter.toChatMessageVO(messageModel);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomIdAndStatus(roomToken, MessageStatus.RECEIVED);
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
