package com.banque.mq.listener;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.impl.MessageBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MqListener {

    private static final Logger logger = LoggerFactory.getLogger(MqListener.class);
    private final MessageBuffer buffer;

    public MqListener(MessageBuffer buffer) {
        this.buffer = buffer;
    }

    @Async
    @JmsListener(destination = "${ibm.mq.queue}")
    public void onMessage(String message) {
        logger.info("Message reçu : {}", message);
        try {
            MessageDTO messageDTO = MessageDTO.builder()
                    .payload(message)
                    .source("IBM MQ")
                    .receivedAt(LocalDateTime.now())
                    .status("RECEIVED")
                    .build();
            buffer.add(messageDTO);
            logger.info("Message sauvegardé avec succès");
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du message MQ : {}", e.getMessage(), e);
        }
    }
}
