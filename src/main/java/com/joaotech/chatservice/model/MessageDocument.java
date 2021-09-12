package com.joaotech.chatservice.model;

import com.joaotech.chatservice.util.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "Message")
public class MessageDocument {

    @Id
    private String id;
    private String token = TokenGenerator.getNew();
    public String roomToken;
    public String userToken;
    public String content;
    public LocalDateTime timestamp;
    public MessageStatus status;
    public MessageType type;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
