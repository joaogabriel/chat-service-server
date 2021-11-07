package com.joaotech.chatservice.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table("messages")
public class MessageModel {

    @PrimaryKey
    @Column
    private UUID id;

    @Column("room_id")
    public UUID roomId;

    @Column("message_owner_token")
    public String messageOwnerToken;

    @Column
    public String content;

    @Column
    public LocalDateTime timestamp;

    @Column("current_status")
    public String currentStatus;

    @Column
    @CassandraType(type = CassandraType.Name.MAP, typeArguments = { CassandraType.Name.TEXT, CassandraType.Name.TIMESTAMP} )
    public Map<String, LocalDateTime> status;

    @Column
    public MessageType type;

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", messageOwnerToken='" + messageOwnerToken + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", currentStatus='" + currentStatus + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
