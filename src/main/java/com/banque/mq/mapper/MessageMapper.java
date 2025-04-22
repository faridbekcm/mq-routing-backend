package com.banque.mq.mapper;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.model.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MessageMapper {

    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "receivedAt", target = "receivedAt")
    @Mapping(source = "status", target = "status")
    MessageDTO toDto(MessageEntity entity);

    @Mapping(source = "payload", target = "payload")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "receivedAt", target = "receivedAt")
    @Mapping(source = "status", target = "status")
    MessageEntity toEntity(MessageDTO dto);

    List<MessageDTO> toDtoList(List<MessageEntity> entities);
    List<MessageEntity> toEntityList(List<MessageDTO> dtos);
}
