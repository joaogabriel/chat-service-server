package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("Message")
public class Message {

    @Column("id")
    private String id;

    @Id
    @Column("token")
    private String token;

    @Column("room_token")
    public String roomToken;

    @Column("user_token")
    public String userToken;

    @Column("content")
    public String content;

    @Column("timestamp")
    public LocalDateTime timestamp;

    @Column("status")
    public MessageStatus status;

    @Column("type")
    public MessageType type;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
