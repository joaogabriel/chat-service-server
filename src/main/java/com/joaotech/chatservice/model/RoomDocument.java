package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Rooms")
public class RoomDocument {

    private UUID id;

    public LocalDateTime startedOn;

    public LocalDateTime closedOn;

    public boolean isClosed;

    public String senderToken;

    public String senderName;

    public String recipientToken;

    public String recipientName;

    public UUID getId() {
        return id;
    }

}
