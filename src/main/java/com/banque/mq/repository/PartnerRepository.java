package com.banque.mq.repository;

import com.banque.mq.model.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistance des entités {@link PartnerEntity}.
 *
 * Fournit automatiquement toutes les opérations CRUD de base via Spring Data JPA :
 * - Création et mise à jour d’un partenaire
 * - Suppression d’un partenaire
 * - Recherche par identifiant
 * - Récupération de la liste complète des partenaires enregistrés
 *
 * Elle est utilisée pour stocker et gérer les partenaires MQ configurables.
 */
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
}
