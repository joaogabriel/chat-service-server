package com.joaotech.chatservice.dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.joaotech.chatservice.ChatServiceApplication;
import com.joaotech.chatservice.model.UserDocument;
import com.joaotech.chatservice.service.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;

import java.security.NoSuchAlgorithmException;

public class UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceApplication.class);

    CqlSession cqlSession = new AppConfig().session();
    CassandraOperations template = new CassandraTemplate(cqlSession);

    InsertOptions insertOptions = org.springframework.data.cassandra.core.InsertOptions.builder().
            consistencyLevel(ConsistencyLevel.LOCAL_QUORUM).
            build();

    public UserDAO() throws NoSuchAlgorithmException {
    }

    public UserDocument findUser(String token) {
        return template.selectOne(token, UserDocument.class);
    }

    public void insert(UserDocument document) {
        template.insert(document, insertOptions);
    }
}
