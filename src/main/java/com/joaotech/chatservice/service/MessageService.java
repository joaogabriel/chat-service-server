package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService {

    private static final String MESSAGE_DESTINATION = "/queue/rooms";

    private final SimpMessagingTemplate messagingTemplate;

    private final RoomService roomService;

    private final MessageRepository messageRepository;

    public void save(CreateMessageVO chatMessage) {

        RoomModel roomModel = roomService.findById(chatMessage.roomId);

        MessageModel messageModel = MessageModel.builder()
                .id(UUID.fromString(chatMessage.messageId))
                .roomId(chatMessage.roomId)
                .messageOwnerToken(chatMessage.userToken)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.SENDED.name())
                .type(chatMessage.type)
                .build();

        messageRepository.save(messageModel);

        notifyInvolved(roomModel, messageModel);

    }

    private void notifyInvolved(RoomModel roomModel, MessageModel messageModel) {

        notifyRoom(roomModel);

        notifyRooms(roomModel, messageModel);

    }

    private void notifyRoom(RoomModel roomModel) {

        RoomsNotificationVO roomsNotificationVO = RoomsNotificationVO.builder()
                .id(roomModel.getId().toString())
                .sender(
                        UserVO.builder()
                                .token(roomModel.senderToken)
                                .name(roomModel.senderName)
                                .build()
                )
                .recipient(
                        UserVO.builder()
                                .token(roomModel.recipientToken)
                                .name(roomModel.recipientName)
                                .build()
                )
                .build();

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION, roomsNotificationVO);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION, roomsNotificationVO);

    }

    private void notifyRooms(RoomModel roomModel, MessageModel messageModel) {

        RoomNotificationVO roomsNotificationVO = RoomNotificationVO.builder()
                .messageId(messageModel.getId().toString())
                .sender(UserVO.builder().token(roomModel.senderToken).build())
                .recipient(UserVO.builder().token(roomModel.recipientToken).build())
                .build();

        Map<String, Object> headers = produceHeaders(messageModel);

        messagingTemplate.convertAndSendToUser(roomModel.recipientToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO, headers);

        messagingTemplate.convertAndSendToUser(roomModel.senderToken, MESSAGE_DESTINATION + "/" + roomModel.getId(), roomsNotificationVO, headers);

    }

    public List<MessageVO> findByRoom(String roomId, Integer page, Integer size) {

        Pageable pageable = CassandraPageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        List<MessageModel> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

        return MessageAdapter.toChatMessageVO(messageModels);

    }

    public MessageVO findById(String id) {

        MessageModel messageModel = messageRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);

        return MessageAdapter.toChatMessageVO(messageModel);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomIdAndStatus(UUID.fromString(roomToken), MessageStatus.DELIVERED);
    }

    private Map<String, Object> produceHeaders(MessageModel messageModel) {
        return Map.of(StompHeaderAccessor.STOMP_MESSAGE_ID_HEADER, messageModel.getId());
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
