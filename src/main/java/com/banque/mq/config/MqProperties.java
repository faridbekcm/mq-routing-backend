package com.banque.mq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
public class MqProperties {
    private String queueManager;
    private String channel;
    private String connName;
    private String user;
    private String password;
    private String queue;
    // Timeout configurations
    private int timeoutConnect = 10;
    private int timeoutReconnect = 30;
    private int timeoutReceive = 5000;
}
