package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("room")
public class RoomModel {

    @PrimaryKey
    @Column
    private String id;

    @Column("started_on")
    public LocalDateTime startedOn;

    @Column("closed_on")
    public LocalDateTime closedOn;

    @Column("is_closed")
    public boolean isClosed;

    @Column("sender_token")
    public String senderToken;

    @Column("sender_name")
    public String senderName;

    @Column("recipient_token")
    public String recipientToken;

    @Column("recipient_name")
    public String recipientName;

    public String getId() {
        return id;
    }

}
