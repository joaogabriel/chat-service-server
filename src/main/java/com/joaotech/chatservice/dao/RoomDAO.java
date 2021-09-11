package com.joaotech.chatservice.dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.joaotech.chatservice.model.Room;
import com.joaotech.chatservice.service.AppConfig;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;

import java.security.NoSuchAlgorithmException;

public class RoomDAO {

    public RoomDAO() throws NoSuchAlgorithmException {
    }


    CqlSession cqlSession = new AppConfig().session();
    CassandraOperations template = new CassandraTemplate(cqlSession);

    InsertOptions insertOptions = org.springframework.data.cassandra.core.InsertOptions.builder().
            consistencyLevel(ConsistencyLevel.LOCAL_QUORUM).
            build();


    public Room findBySender(String sender) {
        return template.selectOne(sender, Room.class);
    }

    public void insert(Room document) {
        template.insert(document, insertOptions);
    }

}
