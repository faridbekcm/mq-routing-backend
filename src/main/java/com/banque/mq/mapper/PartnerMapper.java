package com.banque.mq.mapper;

import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.model.entity.PartnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Interface de mappage entre les entités {@link PartnerEntity} et les objets de transfert {@link PartnerDTO}.
 * Utilise MapStruct pour automatiser la conversion entre les données manipulées par l’IHM ou autre
 * et celles stockées en base de données.
 *
 * Ce mapper permet notamment :
 * - De convertir un partenaire récupéré de la base en DTO pour l'affichage dans l’IHM ou autre
 * - De convertir un DTO de partenaire soumis depuis l’IHM ou autre en entité persistable
 * - De transformer des listes entières dans les deux sens
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PartnerMapper {

    /**
     * Convertit une entité {@link PartnerEntity} en DTO {@link PartnerDTO}.
     *
     * @param entity l’entité issue de la base
     * @return le DTO correspondant, utilisé dans les échanges avec l’IHM ou autre
     */
    PartnerDTO toDto(PartnerEntity entity);

    /**
     * Convertit un objet {@link PartnerDTO} en entité {@link PartnerEntity} à persister.
     *
     * @param dto le partenaire en provenance de l’IHM ou autre
     * @return l’entité prête à être sauvegardée en base
     */
    PartnerEntity toEntity(PartnerDTO dto);

    /**
     * Convertit une liste d’entités {@link PartnerEntity} en liste de DTOs {@link PartnerDTO}.
     *
     * @param entities liste d’objets récupérés depuis la base de données
     * @return liste de DTOs prêts à être envoyés à l’IHM ou autre
     */
    List<PartnerDTO> toDtoList(List<PartnerEntity> entities);
}
