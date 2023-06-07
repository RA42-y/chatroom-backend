package com.ra.chatapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocketConfig is a configuration class for WebSocket support in the application.
 */
@EnableWebSocket
@Configuration
@ComponentScan
public class WebSocketConfig {

    /**
     * Creates a ServerEndpointExporter bean for configuring WebSocket endpoints.
     *
     * @return ServerEndpointExporter bean
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
