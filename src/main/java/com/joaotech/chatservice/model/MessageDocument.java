package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Document(value = "Messages")
public class MessageDocument {

    private UUID id;

    public UUID roomId;

    public String messageOwnerToken;

    public String content;

    public LocalDateTime timestamp;

    public String currentStatus;

    public Map<String, LocalDateTime> status;

    public MessageType type;

    public UUID getId() {
        return id;
    }

}
