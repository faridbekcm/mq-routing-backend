
package com.banque.mq.controller;

import com.banque.mq.listener.MqListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-mq")
public class TestMessageController {

    private static final Logger logger = LoggerFactory.getLogger(TestMessageController.class);

    @Value("${ibm.mq.queue}")
    private String queueName;

    private final JmsTemplate jmsTemplate;

    public TestMessageController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping
    public String sendToMq(@RequestBody String message) {
        try {
            logger.info("Sending message to MQ: {}", message);
            jmsTemplate.convertAndSend("DEV.QUEUE.2", message);
            return "Message envoyé dans la queue MQ : " + message;
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi MQ", e);
            return "Échec de l'envoi";
        }
    }
}
