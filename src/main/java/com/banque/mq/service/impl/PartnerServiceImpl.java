package com.banque.mq.service.impl;

import com.banque.mq.mapper.PartnerMapper;
import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.model.entity.PartnerEntity;
import com.banque.mq.repository.PartnerRepository;
import com.banque.mq.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service métier {@link PartnerService} pour la gestion des partenaires MQ.
 *
 * Ce service permet :
 * - De récupérer une liste paginée des partenaires enregistrés
 * - D’enregistrer un nouveau partenaire ou de mettre à jour un existant
 * - De supprimer un partenaire à partir de son identifiant
 */
@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    /**
     * Constructeur avec injection du repository et du mapper de partenaires.
     *
     * @param partnerRepository accès aux entités persistées
     * @param partnerMapper conversion DTO <-> entité
     */
    public PartnerServiceImpl(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    /**
     * Récupère tous les partenaires sous forme paginée.
     *
     * @param pageable informations de pagination et tri
     * @return une page de partenaires convertis en {@link PartnerDTO}
     */
    @Override
    public Page<PartnerDTO> getAll(Pageable pageable) {
        return partnerRepository.findAll(pageable)
                .map(partnerMapper::toDto);
    }

    /**
     * Enregistre un nouveau partenaire ou met à jour un partenaire existant.
     * Le DTO est converti en entité pour la persistance.
     *
     * @param dto le partenaire à sauvegarder
     */
    @Override
    public void save(PartnerDTO dto) {
        PartnerEntity entity = partnerMapper.toEntity(dto);
        partnerRepository.save(entity);
    }

    /**
     * Supprime un partenaire en base à partir de son identifiant.
     *
     * @param id identifiant unique du partenaire à supprimer
     */
    @Override
    public void delete(Long id) {
        partnerRepository.deleteById(id);
    }
}
