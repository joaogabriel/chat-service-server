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
@Table("Room")
public class RoomModel {

    @Column("id")
    private String id;

    @Id
    @Column("token")
    private String token;

    @Column("started_on")
    public LocalDateTime startedOn;

    @Column("closed_on")
    public LocalDateTime closedOn;

    @Column("sender")
    public String senderToken;

    @Column("recipient")
    public String recipientToken;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

}
