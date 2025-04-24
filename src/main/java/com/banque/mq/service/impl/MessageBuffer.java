package com.banque.mq.service.impl;

import com.banque.mq.model.dto.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Buffer mémoire temporaire utilisé pour stocker les messages MQ reçus avant leur persistance en base.
 *
 * Ce composant utilise une file thread-safe {@link LinkedBlockingQueue} pour permettre une gestion
 * asynchrone des messages entre le listener MQ et le batch de persistance.
 *
 * Avantages :
 * - Évite les écritures immédiates en base à chaque message
 * - Permet un traitement par lot plus performant
 * - Offre une certaine résilience en cas de pic de messages
 */
@Component
public class MessageBuffer {

    private final BlockingQueue<MessageDTO> buffer = new LinkedBlockingQueue<>();

    /**
     * Ajoute un message au buffer.
     * Utilise l’opération non bloquante {@code offer()} pour insérer l’élément.
     *
     * @param dto le message à stocker temporairement
     */
    public void add(MessageDTO dto) {
        buffer.offer(dto);
    }

    /**
     * Vide jusqu’à {@code max} messages du buffer pour traitement.
     *
     * @param max le nombre maximum de messages à extraire
     * @return une liste de messages à traiter (peut être vide)
     */
    public List<MessageDTO> drain(int max) {
        List<MessageDTO> batch = new ArrayList<>();
        buffer.drainTo(batch, max);
        return batch;
    }

    /**
     * Vérifie si le buffer est actuellement vide.
     *
     * @return {@code true} si aucun message n’est en attente, sinon {@code false}
     */
    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}
