package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message")
public class MessageModel {

    @Column
    private String id;

    @Column("room_id")
    public String roomId;

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

}
