package com.joaotech.chatservice.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
public class AppConfig {
// TODO - Corrigir os getEnvs
    private final String username = "keysPaces-dev-at-209085978178";
    private final String password = "C2tp/P3boE3Tz142ZUmVs4laC4KDHHPXRYeOwDgjWpg=";
    File driverConfig = new File(System.getProperty("user.dir") + "/src/main/resources/application.conf");

    @Primary
    public @Bean
    CqlSession session() throws NoSuchAlgorithmException {
        return CqlSession.builder().
                withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
                withAuthCredentials(username, password).
                withSslContext(SSLContext.getDefault()).
                withKeyspace("ChatService").
                build();
    }

}
