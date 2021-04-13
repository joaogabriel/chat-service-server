package com.joaotech.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotificationDocument {

    private String id;
    private String senderId;
    private String senderName;

}
