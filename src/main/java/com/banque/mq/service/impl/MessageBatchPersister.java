package com.banque.mq.service.impl;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Composant responsable de la persistance des messages MQ de manière asynchrone et par lot.
 *
 * Cette classe interroge régulièrement le buffer mémoire {@link MessageBuffer} à intervalles réguliers
 * (chaque seconde) afin d’en extraire un groupe de messages et les persister en base de données
 * via le service {@link MessageService}.
 *
 * Ce mécanisme permet :
 * - De gérer des pics de messages sans surcharge immédiate de la base
 * - De regrouper les écritures pour améliorer les performances
 */
@Slf4j
@Component
public class MessageBatchPersister {

    private final MessageBuffer buffer;
    private final MessageService messageService;

    /**
     * Constructeur avec injection du buffer de messages et du service de persistance.
     *
     * @param buffer le tampon mémoire contenant les messages à traiter
     * @param messageService service chargé de sauvegarder les messages en base
     */
    public MessageBatchPersister(MessageBuffer buffer, MessageService messageService) {
        this.buffer = buffer;
        this.messageService = messageService;
    }

    /**
     * Méthode planifiée qui s’exécute toutes les secondes.
     * Elle vérifie si des messages sont en attente dans le buffer, puis les vide par groupes de 100
     * pour les sauvegarder de manière groupée en base de données.
     *
     * Cette approche améliore les performances globales et assure une bonne résilience.
     */
    @Scheduled(fixedRate = 1000)
    public void flushBatch() {
        if (!buffer.isEmpty()) {
            List<MessageDTO> batch = buffer.drain(100);
            if (!batch.isEmpty()) {
                log.info("Persisting batch of {} messages", batch.size());
                messageService.saveAll(batch);
            }
        }
    }
}
