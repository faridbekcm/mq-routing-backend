package com.banque.mq.service;

import com.banque.mq.model.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface définissant les opérations métier liées aux messages MQ.
 * Elle est implémentée par {@link com.banque.mq.service.impl.MessageServiceImpl}.
 *
 * Cette interface permet de :
 * - Rechercher un message par son identifiant
 * - Filtrer les messages selon différents critères (source, statut, date)
 * - Sauvegarder des messages par lot
 */
public interface MessageService {

    /**
     * Récupère un message à partir de son identifiant.
     *
     * @param id identifiant du message
     * @return le message sous forme de {@link MessageDTO}, ou {@code null} si non trouvé
     */
    MessageDTO getMessageById(Long id);

    /**
     * Recherche paginée de messages MQ selon des critères dynamiques.
     *
     * @param pageable informations de pagination (page, taille, tri)
     * @param source filtre sur la source du message (facultatif)
     * @param status filtre sur le statut du message (facultatif)
     * @param receivedAt filtre sur la date de réception minimale (facultatif)
     * @return une page de {@link MessageDTO} correspondant aux critères fournis
     */
    Page<MessageDTO> getFilteredMessages(Pageable pageable, String source, String status, LocalDateTime receivedAt);

    /**
     * Sauvegarde une liste de messages MQ en base de données.
     *
     * @param messages liste des messages à persister
     */
    void saveAll(List<MessageDTO> messages);
}
