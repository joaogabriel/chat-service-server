package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatusType;
import com.joaotech.chatservice.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageStatusService {

    private final MessageRepository messageRepository;

    public void updateDeliveredStatus(String messageId) {
        MessageModel message = messageRepository.findById(UUID.fromString(messageId)).orElse(null);

        String currentStatus = MessageStatusType.DELIVERED.name();

        message.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(message);

    }

}
