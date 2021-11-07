package com.joaotech.chatservice.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.adapter.PaginatedMessagesAdapter;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.PaginatedMessagesVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class MessageService {

    private final CqlSession cqlSession;

    private final RoomService roomService;

    private final NotificationService notificationService;

    private final MessageRepository messageRepository;

    public void create(CreateMessageVO chatMessage) {

        RoomModel roomModel = roomService.findById(chatMessage.roomId);

        String currentStatus = MessageStatus.SENDED.name();

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

        messageModel.status.put(MessageStatus.NOT_SENDED.name(), LocalDateTime.ofEpochSecond(chatMessage.timestamp, 0, ZoneOffset.UTC));

        messageModel.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(messageModel);

        notificationService.notifyInvolved(roomModel, messageModel);

    }

    public PaginatedMessagesVO findByRoom(String roomId, String cursorMark) {

        Statement statement = SimpleStatement.builder("SELECT * FROM messages WHERE room_id = " + roomId + " ORDER BY timestamp DESC")
                .setPageSize(30)
                .build();

        if (StringUtils.isNotEmpty(cursorMark)) {

            PagingState pagingState = PagingState.fromString(cursorMark);

            statement = statement.setPagingState(pagingState);

        }

        ResultSet resultSet = cqlSession.execute(statement);

        PagingState safePagingState = resultSet.getExecutionInfo().getSafePagingState();

        List<MessageVO> messages = new ArrayList<>();

        int remaining = resultSet.getAvailableWithoutFetching();

        for (Row row : resultSet) {

            messages.add(PaginatedMessagesAdapter.toMessageVO(row));

            if (--remaining == 0) {
                break;
            }

        }

        return PaginatedMessagesAdapter.toPaginatedMessagesVO(messages, safePagingState);

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
        return messageRepository.countByRoomIdAndCurrentStatus(UUID.fromString(roomToken), MessageStatus.DELIVERED.name());
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
