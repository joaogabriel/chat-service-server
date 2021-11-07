package com.joaotech.chatservice.adapter;

import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.core.cql.Row;
import com.joaotech.chatservice.model.MessageType;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.PaginatedMessagesVO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class PaginatedMessagesAdapter {

    public static PaginatedMessagesVO toPaginatedMessagesVO(List<MessageVO> messages, PagingState pagingState) {

        return PaginatedMessagesVO.builder()
                .messages(messages)
                .count(messages.size())
                .cursorMark(pagingState == null ? null : pagingState.toString())
                .build();

    }

    public static MessageVO toMessageVO(Row row) {

        return MessageVO.builder()
                .id(convertyUUID(row.getUuid("id")))
                .roomId(convertyUUID(row.getUuid("room_id")))
                .content(row.getString("content"))
                .currentStatus(row.getString("current_status"))
                .userToken(row.getString("message_owner_token"))
                .timestamp(convertyTimestamp(row.getInstant("timestamp")))
                .type(convertyMessageType(row.getString("type")))
                .build();

    }

    private static String convertyUUID(UUID uuid) {

        if (uuid == null) {
            return null;
        }

        return uuid.toString();

    }

    private static MessageType convertyMessageType(String type) {

        if (type == null) {
            return null;
        }

        return MessageType.valueOf(type);

    }

    private static LocalDateTime convertyTimestamp(Instant timestamp) {

        if (timestamp == null) {
            return null;
        }

        return LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC);

    }

}
