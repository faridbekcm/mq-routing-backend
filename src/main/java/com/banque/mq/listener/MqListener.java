package com.banque.mq.listener;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.impl.MessageBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Composant Spring chargé d'écouter les messages entrants sur la file IBM MQ.
 * Lorsqu’un message est reçu, il est transformé en objet {@link MessageDTO}, enrichi de métadonnées,
 * puis stocké temporairement dans un buffer en mémoire avant d’être persisté.
 *
 * Cette classe agit comme un point d'entrée asynchrone entre le monde MQ et le système applicatif.
 */
@Component
public class MqListener {

    private static final Logger logger = LoggerFactory.getLogger(MqListener.class);

    private final MessageBuffer buffer;

    /**
     * Constructeur avec injection du buffer de messages.
     *
     * @param buffer structure temporaire permettant de stocker les messages MQ reçus avant traitement.
     */
    public MqListener(MessageBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Méthode asynchrone déclenchée automatiquement à la réception d’un message sur la file MQ configurée.
     * Elle transforme le message brut en un objet {@link MessageDTO}, enrichi de la date, source et statut,
     * puis le stocke dans le buffer applicatif.
     *
     * @param message contenu brut du message reçu depuis la file MQ
     */
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
