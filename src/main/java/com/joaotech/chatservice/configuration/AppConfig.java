package com.joaotech.chatservice.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

import java.io.File;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints("172.17.20.34");
        session.setKeyspaceName("chat_service_dev");

        return session;
    }

}
