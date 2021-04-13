package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.ChatMessageDocument;
import com.joaotech.chatservice.model.ChatRoomDocument;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.repository.ChatMessageRepository;
import com.joaotech.chatservice.vo.ChatMessageVO;
import com.joaotech.chatservice.vo.ChatNotificationVO;
import com.sun.jdi.PrimitiveValue;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private static final String MESSAGE_DESTINATION = "/queue/messages";

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final ChatRoomService chatRoomService;
    private final MongoOperations mongoOperations;

    public void save(ChatMessageVO chatMessage) {
        ChatRoomDocument roomDocument = chatRoomService.findByToken(chatMessage.roomToken);

        if (roomDocument == null) {
            throw new RuntimeException();
        }

        ChatMessageDocument chatMessageDocument = ChatMessageDocument.builder()
                .roomToken(chatMessage.roomToken)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.RECEIVED)
                .type(chatMessage.type)
                .build();

        chatMessageRepository.save(chatMessageDocument);

        ChatNotificationVO chatNotification = new ChatNotificationVO(chatMessageDocument.getToken(), roomDocument.sender.getToken(), roomDocument.sender.name);

        messagingTemplate.convertAndSendToUser(roomDocument.recipient.getToken(), MESSAGE_DESTINATION, chatNotification);

    }

    public long countNewMessages(String senderId, String recipientId) {
//        return repository.countBySenderIdAndRecipientIdAndStatus(
//                senderId, recipientId, MessageStatus.RECEIVED);
        return 1;
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
