package com.joaotech.chatservice.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.protocol.internal.request.query.QueryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;

@Configuration
public class AppConfig {

    private final File driverConfig = new File(System.getProperty("user.dir") + "/src/main/resources/application.conf");

    @Bean
    @Primary
    public CqlSession session() {
        return CqlSession.builder()
                .withConfigLoader(DriverConfigLoader.fromFile(driverConfig))
                .withKeyspace("chat_service_dev")
                .build();
    }

}
