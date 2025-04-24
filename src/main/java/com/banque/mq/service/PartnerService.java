package com.banque.mq.service;

import com.banque.mq.model.dto.PartnerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface du service métier pour la gestion des partenaires MQ.
 * Implémentée par {@link com.banque.mq.service.impl.PartnerServiceImpl}.
 *
 * Permet de gérer les opérations suivantes :
 * - Consultation paginée des partenaires configurés
 * - Enregistrement (création ou mise à jour) d’un partenaire
 * - Suppression d’un partenaire
 */
public interface PartnerService {

    /**
     * Récupère tous les partenaires enregistrés sous forme paginée.
     *
     * @param pageable informations de pagination et de tri
     * @return une page d’objets {@link PartnerDTO}
     */
    Page<PartnerDTO> getAll(Pageable pageable);

    /**
     * Sauvegarde un nouveau partenaire ou met à jour un partenaire existant.
     *
     * @param dto les données du partenaire à enregistrer
     */
    void save(PartnerDTO dto);

    /**
     * Supprime un partenaire à partir de son identifiant.
     *
     * @param id identifiant du partenaire à supprimer
     */
    void delete(Long id);
}
