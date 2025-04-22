package com.banque.mq.service;

import com.banque.mq.model.dto.PartnerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    Page<PartnerDTO> getAll(Pageable pageable);
    void save(PartnerDTO dto);
    void delete(Long id);
}