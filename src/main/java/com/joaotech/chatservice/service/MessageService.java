package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public void create(CreateMessageVO chatMessage) {

        RoomDocument roomDocument = roomService.findById(chatMessage.roomId);

        String currentStatus = MessageStatus.SENDED.name();

        MessageDocument messageDocument = MessageDocument.builder()
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

        messageDocument.status.put(MessageStatus.NOT_SENDED.name(), LocalDateTime.ofEpochSecond(chatMessage.timestamp, 0, ZoneOffset.UTC));

        messageDocument.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(messageDocument);

        notificationService.notifyInvolved(roomDocument, messageDocument);

    }

    public List<MessageVO> findByRoom(String roomId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        Slice<MessageDocument> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

        return MessageAdapter.toChatMessageVO(messageModels.getContent());

    }

    public long countByRoom(String roomId) {
        if (StringUtils.isEmpty(roomId)) throw new RuntimeException("roomId is required");
        return messageRepository.countByRoomId(UUID.fromString(roomId));
    }

    public MessageVO findById(String id) {

        MessageDocument messageDocument = messageRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);

        return MessageAdapter.toChatMessageVO(messageDocument);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomIdAndStatus(UUID.fromString(roomToken), MessageStatus.DELIVERED.name());
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
