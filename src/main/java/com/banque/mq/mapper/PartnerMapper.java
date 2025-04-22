package com.banque.mq.mapper;

import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.model.entity.PartnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PartnerMapper {
    PartnerDTO toDto(PartnerEntity entity);
    PartnerEntity toEntity(PartnerDTO dto);
    List<PartnerDTO> toDtoList(List<PartnerEntity> entities);
}
