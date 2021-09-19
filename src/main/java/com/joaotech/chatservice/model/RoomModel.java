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
@Table("room")
public class RoomModel {

    @Id
    @Column
    private String id;

    @Column("started_on")
    public LocalDateTime startedOn;

    @Column("closed_on")
    public LocalDateTime closedOn;

    @Column("sender_id")
    public String senderId;

    @Column("recipient_id")
    public String recipientId;

    public String getId() {
        return id;
    }

}
