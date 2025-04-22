package com.banque.mq.repository;

import com.banque.mq.model.entity.MessageEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MessageSpecifications {

    public static Specification<MessageEntity> buildFilter(String source, String status, LocalDateTime receivedAt) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if (source != null && !source.isBlank()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("source"), source));
            }
            if (status != null && !status.isBlank()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
            }
            if (receivedAt != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("receivedAt"), receivedAt));
            }

            return predicate;
        };
    }
}