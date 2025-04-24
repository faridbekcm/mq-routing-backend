package com.banque.mq.service.impl;

import com.banque.mq.mapper.MessageMapper;
import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.model.entity.MessageEntity;
import com.banque.mq.repository.MessageRepository;
import com.banque.mq.repository.MessageSpecifications;
import com.banque.mq.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service métier {@link MessageService} chargé de gérer les messages MQ.
 *
 * Cette classe assure :
 * - La conversion des entités vers des DTOs (et inversement)
 * - Le filtrage paginé des messages via des critères dynamiques (source, statut, date)
 * - La persistance par lot des messages reçus
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    /**
     * Constructeur avec injection du repository et du mapper.
     *
     * @param messageRepository accès aux données persistées
     * @param messageMapper conversion entité <-> DTO
     */
    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    /**
     * Récupère un message en base de données par son identifiant.
     * Retourne un objet {@link MessageDTO} ou {@code null} si l’ID est inconnu.
     *
     * @param id identifiant unique du message
     * @return le message converti en DTO, ou {@code null}
     */
    @Override
    public MessageDTO getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto)
                .orElse(null);
    }

    /**
     * Recherche paginée de messages en base avec des critères dynamiques.
     *
     * @param pageable configuration de pagination (page, taille, tri)
     * @param source filtre sur la source du message (facultatif)
     * @param status filtre sur le statut du message (facultatif)
     * @param receivedAt filtre sur la date de réception minimale (facultatif)
     * @return une page de {@link MessageDTO} correspondant aux critères
     */
    @Override
    public Page<MessageDTO> getFilteredMessages(Pageable pageable, String source, String status, LocalDateTime receivedAt) {
        return messageRepository.findAll(
                MessageSpecifications.buildFilter(source, status, receivedAt), pageable
        ).map(messageMapper::toDto);
    }

    /**
     * Persiste une liste de messages en base de données.
     * Les DTOs sont d’abord convertis en entités, puis sauvegardés par lot.
     *
     * @param messages liste de messages à sauvegarder
     */
    @Override
    public void saveAll(List<MessageDTO> messages) {
        List<MessageEntity> entities = messages.stream()
                .map(messageMapper::toEntity)
                .collect(Collectors.toList());
        messageRepository.saveAll(entities);
    }
}
