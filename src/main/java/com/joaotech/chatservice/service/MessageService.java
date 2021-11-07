package com.joaotech.chatservice.service;

import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.joaotech.chatservice.adapter.MessageAdapter;
import com.joaotech.chatservice.model.MessageModel;
import com.joaotech.chatservice.model.MessageStatus;
import com.joaotech.chatservice.model.RoomModel;
import com.joaotech.chatservice.repository.MessageRepository;
import com.joaotech.chatservice.vo.CreateMessageVO;
import com.joaotech.chatservice.vo.MessageVO;
import com.joaotech.chatservice.vo.PaginatedMessagesVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class MessageService {

    @Autowired
    private CassandraOperations cassandraOperations;

    private final RoomService roomService;

    private final NotificationService notificationService;

    private final MessageRepository messageRepository;

    public void create(CreateMessageVO chatMessage) {

        RoomModel roomModel = roomService.findById(chatMessage.roomId);

        String currentStatus = MessageStatus.SENDED.name();

        MessageModel messageModel = MessageModel.builder()
                .id(UUID.fromString(chatMessage.messageId))
                .roomId(UUID.fromString(chatMessage.roomId))
                .roomId(UUID.fromString(chatMessage.roomId))
                .messageOwnerToken(chatMessage.messageOwnerToken)
                .currentStatus(currentStatus)
                .content(chatMessage.content)
                .timestamp(LocalDateTime.now())
                .type(chatMessage.type)
                .status(new HashMap<>())
                .build();

        messageModel.status.put(MessageStatus.NOT_SENDED.name(), LocalDateTime.ofEpochSecond(chatMessage.timestamp, 0, ZoneOffset.UTC));

        messageModel.status.put(currentStatus, LocalDateTime.now());

        messageRepository.save(messageModel);

        notificationService.notifyInvolved(roomModel, messageModel);

    }

    public PaginatedMessagesVO findByRoom(String roomId, Integer page, Integer size, String cursorMark) {

        testMedium(roomId, page, size, cursorMark);

        if (true) return null;

        String DEFAULT_CURSOR_MARK = "-1";

//        cursorMark = "java.nio.HeapByteBufferR[pos=0 lim=22 cap=22]";

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        ByteBuffer pagingState = DEFAULT_CURSOR_MARK.equalsIgnoreCase(cursorMark) ? null : PagingState.fromString(cursorMark).getRawPagingState();

        Pageable pageable = CassandraPageRequest.of(pageRequest, pagingState);

//        Pageable pageable = CassandraPageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        Slice<MessageModel> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

//        String newCursorMark = (((CassandraPageRequest) messageModels.getPageable()).getPagingState().toString());
        String nextCursorMark = null;

        if (messageModels.isLast()) {
            nextCursorMark = DEFAULT_CURSOR_MARK;
        } else {
            nextCursorMark = (((CassandraPageRequest) messageModels.getPageable()).getPagingState().toString());
        }

        ByteBuffer pagingState2 = ((CassandraPageRequest) messageModels.getPageable()).getPagingState();
        String s = new String(pagingState2.array());
        System.out.println(s);

        CassandraPageRequest pageRequest1 = (CassandraPageRequest) messageModels.nextPageable();
        String pagingState1 = Objects.requireNonNull(pageRequest1.getPagingState()).toString();

        return MessageAdapter.toPaginatedMessagesVO(messageModels.getContent(), nextCursorMark);

    }

    public void testMedium(String roomId, Integer page, Integer size, String cursorMark) {

        String DEFAULT_CURSOR_MARK = "-1";

//        cursorMark = "1|2|3|4|5";

        Pageable pageable = CassandraPageRequest.of(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")
        ), DEFAULT_CURSOR_MARK.equalsIgnoreCase(cursorMark) ? null : (ByteBuffer) PagingState.fromString(cursorMark));

        Slice<MessageModel> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

        List<MessageModel> content = messageModels.getContent();

        content.forEach(System.out::println);

        if (messageModels.isLast()) {
            System.out.println(DEFAULT_CURSOR_MARK);
        } else {
            ByteBuffer pagingState = ((CassandraPageRequest) messageModels.getPageable()).getPagingState();
            Pageable outroPageable = CassandraPageRequest.of(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")
            ), pagingState);

            // 1
//            Charset charset = StandardCharsets.US_ASCII;
            Charset charset = Charset.defaultCharset();
//            CharBuffer charBuffer = charset.decode(pagingState);
//            String s1 = charBuffer.toString();
//            PagingState pagingState1 = PagingState.fromString(s1);
//            System.out.println(s1);

            // 2
//            pagingState.position(0);
//            byte[] bytes = new byte[pagingState.remaining()];
//            pagingState.get(bytes);
//            String s2 = new String(bytes, charset);
//            PagingState pagingState2 = PagingState.fromBytes(bytes);
//            System.out.println(s2);

            // 3
//            byte[] bytes = new byte[pagingState.remaining()];
//            pagingState.duplicate().get(bytes);
//            PagingState pagingState3 = PagingState.fromBytes(bytes);
//
//            Pageable outroOutroPageable = CassandraPageRequest.of(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")
//            ), pagingState3.getRawPagingState());
//
//            Slice<MessageModel> messageModels2 = messageRepository.findByRoomId(UUID.fromString(roomId), outroOutroPageable);
//
//            List<MessageModel> content2 = messageModels2.getContent();
//
//            content2.forEach(System.out::println);

            // 4
//            new DefaultPagingState(pagingState, statement, AttachmentPoint.NONE);

            // 5
//            ByteBuffer buffer = ByteBuffer.allocate(this.rawPagingState.remaining() + this.hash.length + 6);
//            buffer.putShort((short)this.rawPagingState.remaining());
//            buffer.putShort((short)this.hash.length);
//            buffer.put(this.rawPagingState.duplicate());
//            buffer.put(this.hash);
//            buffer.putShort((short)this.protocolVersion);
//            buffer.rewind();
//            return buffer.array();

            // o que tentar ainda:
            // fazendo a consulta e recuperando o ResultSet, da pra acessar o PagingIterable, getExecutionInfo() e getSafePagingState()

            ResultSet resultSet = cassandraOperations.getCqlOperations().queryForResultSet("SELECT * FROM messages WHERE room_id = 2cc80127-84c1-4992-91b2-bb7cfd2cf105 ORDER BY timestamp DESC");
            PagingState safePagingState = resultSet.getExecutionInfo().getSafePagingState();
            System.out.println(safePagingState);
            resultSet.forEach(row -> System.out.println(row.getString("content")));
        }

    }

    public void testComPage(String roomId, Integer page, Integer size, String cursorMark) {

        Pageable pageable = CassandraPageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        Slice<MessageModel> messageModels = messageRepository.findByRoomId(UUID.fromString(roomId), pageable);

        List<MessageModel> content = messageModels.getContent();

        content.forEach(System.out::println);

    }

    public long countByRoom(String roomId) {
        if (StringUtils.isEmpty(roomId)) throw new RuntimeException("roomId is required");
        return messageRepository.countByRoomId(UUID.fromString(roomId));
    }

    public MessageVO findById(String id) {

        MessageModel messageModel = messageRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);

        return MessageAdapter.toChatMessageVO(messageModel);

    }

    public long countNewMessages(String roomToken) {
        return messageRepository.countByRoomIdAndStatus(UUID.fromString(roomToken), MessageStatus.DELIVERED.name());
    }

//    public List<ChatMessageDocument> findChatMessages(String senderId, String recipientId) {
//        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);
//
//        List<ChatMessageDocument> messages = chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());
//
//        if (messages.size() > 0) {
//            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
//        }
//
//        return messages;
//    }
//
//    public ChatMessageDocument findById(String id) {
//        return repository
//                .findById(id)
//                .map(chatMessageDocument -> {
//                    chatMessageDocument.setStatus(MessageStatus.DELIVERED);
//                    return repository.save(chatMessageDocument);
//                })
//                .orElseThrow(() ->
//                        new RuntimeException("can't find message (" + id + ")")); // alterei aqui
//    }
//
//    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
//        Query query = new Query(
//                Criteria
//                        .where("senderId").is(senderId)
//                        .and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessageDocument.class);
//    }

}
