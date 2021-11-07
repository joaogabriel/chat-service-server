package com.joaotech.chatservice.service;

import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.model.MessageStatus;
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
        MessageDocument message = messageRepository.findById(UUID.fromString(messageId)).orElseThrow(RuntimeException::new);

        String currentStatus = MessageStatus.DELIVERED.name();

        message.currentStatus = currentStatus;

        message.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(message);

    }

}
