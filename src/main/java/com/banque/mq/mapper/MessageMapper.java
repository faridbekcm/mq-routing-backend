package com.banque.mq.mapper;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.model.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Interface de mappage entre les entités {@link MessageEntity} et les objets de transfert {@link MessageDTO}.
 * Utilise MapStruct pour générer automatiquement le code de conversion entre les couches base de données et présentation.
 *
 * Permet notamment :
 * - De transformer un message en base (entity) en objet destiné à l’IHM ou autre (DTO)
 * - De reconvertir un DTO en entité pour la persistance
 * - De mapper des listes complètes d’objets
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MessageMapper {

    /**
     * Convertit une entité {@link MessageEntity} en DTO {@link MessageDTO}.
     *
     * @param entity l'entité message issue de la base de données
     * @return le DTO prêt à être retourné à l’IHM ou autre
     */
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "receivedAt", target = "receivedAt")
    @Mapping(source = "status", target = "status")
    MessageDTO toDto(MessageEntity entity);

    /**
     * Convertit un DTO {@link MessageDTO} en entité {@link MessageEntity}.
     *
     * @param dto le message reçu ou manipulé côté IHM
     * @return l'entité prête à être persistée en base
     */
    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "receivedAt", target = "receivedAt")
    @Mapping(source = "status", target = "status")
    MessageEntity toEntity(MessageDTO dto);

    /**
     * Convertit une liste d’entités messages en liste de DTOs.
     *
     * @param entities liste d’entités message
     * @return liste correspondante de DTOs
     */
    List<MessageDTO> toDtoList(List<MessageEntity> entities);

    /**
     * Convertit une liste de DTOs messages en liste d’entités.
     *
     * @param dtos liste de messages en provenance de l’IHM ou autre
     * @return liste d'entités prêtes à être enregistrées
     */
    List<MessageEntity> toEntityList(List<MessageDTO> dtos);
}
