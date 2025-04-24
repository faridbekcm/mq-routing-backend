package com.banque.mq.repository;

import com.banque.mq.model.entity.MessageEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Classe utilitaire contenant des spécifications dynamiques
 * pour filtrer les messages stockés en base de données.
 *
 * Utilise l’API {@link Specification} de Spring Data JPA
 * pour construire dynamiquement des requêtes selon les critères fournis.
 */
public class MessageSpecifications {

    /**
     * Construit une spécification permettant de filtrer les messages
     * par source, statut et date de réception minimale.
     *
     * Cette méthode permet notamment à l’API de recherche de messages
     * de renvoyer uniquement les résultats pertinents selon les filtres utilisateurs.
     *
     * @param source la source du message (ex. : "IBM MQ")
     * @param status le statut du message (ex. : "RECEIVED", "PROCESSED")
     * @param receivedAt la date minimale de réception à inclure
     * @return une {@link Specification} combinant les critères
     */
    public static Specification<MessageEntity> buildFilter(String source, String status, LocalDateTime receivedAt) {
        return (root, query, criteriaBuilder) -> {
            // Conjonction de base (équivalent à WHERE TRUE)
            var predicate = criteriaBuilder.conjunction();

            if (source != null && !source.isBlank()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("source"), source));
            }

            if (status != null && !status.isBlank()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("status"), status));
            }

            if (receivedAt != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("receivedAt"), receivedAt));
            }

            return predicate;
        };
    }
}
