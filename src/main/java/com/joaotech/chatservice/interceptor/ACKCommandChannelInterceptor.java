package com.joaotech.chatservice.interceptor;

import com.joaotech.chatservice.service.MessageStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class ACKCommandChannelInterceptor implements ChannelInterceptor {

    private MessageStatusService messageStatusService;

    @Autowired
    public ACKCommandChannelInterceptor(MessageStatusService messageStatusService) {
        this.messageStatusService = messageStatusService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        boolean invalidACKCommand = accessor.getCommand() == null || !accessor.getCommand().equals(StompCommand.ACK);

        boolean invalidMessageId = StringUtils.isEmpty(accessor.getMessageId());

        if (invalidACKCommand || invalidMessageId) {
            return message;
        }

        messageStatusService.updateDeliveredStatus(accessor.getMessageId());

        return message;

    }

}
