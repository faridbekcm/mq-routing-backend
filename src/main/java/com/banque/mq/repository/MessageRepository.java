package com.banque.mq.repository;

import com.banque.mq.model.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Interface de persistance des entités {@link MessageEntity}.
 *
 * Cette interface permet :
 * - D’accéder aux méthodes CRUD standards de Spring Data (findAll, save, delete, etc.)
 * - D’utiliser des spécifications dynamiques via {@link JpaSpecificationExecutor} pour des recherches filtrées
 *
 * Elle est utilisée pour enregistrer et consulter les messages MQ reçus et stockés dans la base de données.
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long>, JpaSpecificationExecutor<MessageEntity> {
}
