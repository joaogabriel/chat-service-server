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
@Table("message")
public class MessageModel {

    @Id
    @Column
    private String id;

    @Column
    private String token;

    @Column("room_token")
    public String roomToken;

    @Column("user_token")
    public String userToken;

    @Column
    public String content;

    @Column
    public LocalDateTime timestamp;

    @Column
    public MessageStatus status;

    @Column
    public MessageType type;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
