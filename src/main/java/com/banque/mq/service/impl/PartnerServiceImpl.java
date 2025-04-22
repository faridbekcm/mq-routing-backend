package com.banque.mq.service.impl;

import com.banque.mq.mapper.PartnerMapper;
import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.model.entity.PartnerEntity;
import com.banque.mq.repository.PartnerRepository;
import com.banque.mq.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public PartnerServiceImpl(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    @Override
    public Page<PartnerDTO> getAll(Pageable pageable) {
        return partnerRepository.findAll(pageable)
                .map(partnerMapper::toDto);
    }

    @Override
    public void save(PartnerDTO dto) {
        PartnerEntity entity = partnerMapper.toEntity(dto);
        partnerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        partnerRepository.deleteById(id);
    }
}