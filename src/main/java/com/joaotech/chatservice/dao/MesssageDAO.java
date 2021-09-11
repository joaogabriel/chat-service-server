package com.joaotech.chatservice.dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.joaotech.chatservice.model.MessageDocument;
import com.joaotech.chatservice.service.AppConfig;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;

import java.security.NoSuchAlgorithmException;

public class MesssageDAO {

    CqlSession cqlSession = new AppConfig().session();
    CassandraOperations template = new CassandraTemplate(cqlSession);

    InsertOptions insertOptions = org.springframework.data.cassandra.core.InsertOptions.builder().
            consistencyLevel(ConsistencyLevel.LOCAL_QUORUM).
            build();

    public MesssageDAO() throws NoSuchAlgorithmException {
    }


    public MessageDocument findUser(String token) {
        return template.selectOne(token, MessageDocument.class);
    }

    public void insert(MessageDocument document) {
        template.insert(document, insertOptions);
    }

}
