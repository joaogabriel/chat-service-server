package com.joaotech.chatservice.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {
    File driverConfig = new File(System.getProperty("user.dir") + "/src/main/resources/application.conf");

    @Primary
    public @Bean
    CqlSession session() throws NoSuchAlgorithmException {
        return CqlSession.builder().
                withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
                withKeyspace("chat_service_model").
                build();
    }

}
