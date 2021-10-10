package com.joaotech.chatservice.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class ACKCommandChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        MessageHeaders headers = message.getHeaders();

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        boolean invalidACKCommand = accessor.getCommand() == null || !accessor.getCommand().equals(StompCommand.ACK);

        boolean invalidMessageId = StringUtils.isEmpty(accessor.getMessageId());

        if (invalidACKCommand || invalidMessageId) {
            return message;
        }

        String messageId = accessor.getMessageId();
        String messageString = accessor.getMessage();

        // TODO: 25/09/21 update status message

        return message;

    }

}
