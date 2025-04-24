package com.banque.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principale de l’application de routage MQ.
 *
 * */
@SpringBootApplication
@EnableScheduling
public class MqRouterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqRouterApplication.class, args);
    }
}
