package com.joaotech.chatservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints("172.17.20.34:9042");
        session.setKeyspaceName("chat_service_dev");

        return session;
    }

}
