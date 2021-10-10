package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatusType;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService {

    private final RoomService roomService;

    private final NotificationService notificationService;

    private final MessageRepository messageRepository;

    public void save(CreateMessageVO chatMessage) {

        RoomModel roomModel = roomService.findById(chatMessage.roomId);

        String currentStatus = MessageStatusType.SENDED.name();

        MessageModel messageModel = MessageModel.builder()
                .id(UUID.fromString(chatMessage.messageId))
                .roomId(UUID.fromString(chatMessage.roomId))
                .roomId(UUID.fromString(chatMessage.roomId))
                .messageOwnerToken(chatMessage.messageOwnerToken)
                .currentStatus(currentStatus)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .type(chatMessage.type)
                .status(new HashMap<>())
                .build();

        messageModel.status.put(MessageStatusType.NOT_SENDED.name(), LocalDateTime.ofEpochSecond(chatMessage.timestamp,0 , ZoneOffset.UTC));

        messageModel.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(messageModel);

        notificationService.notifyInvolved(roomModel, messageModel);

    }

    public List<MessageVO> findByRoom(String roomId, Integer page, Integer size) {

        Pageable pageable = CassandraPageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        List<MessageModel> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

        return MessageAdapter.toChatMessageVO(messageModels);

    }

    public long countByRoom(String roomId) {
        if (StringUtils.isEmpty(roomId)) throw new RuntimeException("roomId is required");
        return messageRepository.countByRoomId(UUID.fromString(roomId));
    }

    public MessageVO findById(String id) {

        MessageModel messageModel = messageRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);

        return MessageAdapter.toChatMessageVO(messageModel);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomIdAndStatus(UUID.fromString(roomToken), MessageStatusType.DELIVERED.name());
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
