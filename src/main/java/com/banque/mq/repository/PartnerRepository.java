package com.banque.mq.repository;

import com.banque.mq.model.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
}