package com.joaotech.chatservice.service;

import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomDocument;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.util.TokenGenerator;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.UserNotificationVO;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private static final String MESSAGE_DESTINATION = "/queue/messages";

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final RoomService roomService;
    private final MongoOperations mongoOperations;

    public void save(CreateMessageVO chatMessage) {

        RoomDocument roomDocument = roomService.findByToken(chatMessage.roomToken);

        if (roomDocument == null) {
            // TODO: 26/08/21 lancar excecao especifica
            throw new RuntimeException();
        }

        MessageDocument messageDocument = MessageDocument.builder()
                .token(TokenGenerator.getNew())
                .roomToken(chatMessage.roomToken)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.RECEIVED)
                .type(chatMessage.type)
                .build();

        messageRepository.save(messageDocument);

        // TODO: 26/08/21 usar builder
        // TODO: 26/08/21 id com token?
        UserNotificationVO chatNotification = new UserNotificationVO(messageDocument.getToken(), roomDocument.sender.token, roomDocument.sender.name);

        messagingTemplate.convertAndSendToUser(roomDocument.recipient.token, MESSAGE_DESTINATION, chatNotification);

    }

    public List<MessageVO> findByRoom(String roomToken) {

        List<MessageDocument> messages = messageRepository.findByRoomToken(roomToken);

        return MessageAdapter.toChatMessageVO(messages);

    }

    public MessageVO findByToken(String token) {

        MessageDocument message = messageRepository.findByToken(token);

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
