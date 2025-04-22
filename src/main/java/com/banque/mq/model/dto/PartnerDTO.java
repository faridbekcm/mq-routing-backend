package com.banque.mq.model.dto;

import com.banque.mq.model.entity.PartnerEntity.Direction;
import com.banque.mq.model.entity.PartnerEntity.ProcessedFlowType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDTO {

    @NotBlank(message = "Alias est obligatoire ")
    private String alias;

    @NotBlank(message = "Type est obligatoire ")
    private String type;

    @NotNull(message = "Direction est obligatoire  (INBOUND or OUTBOUND)")
    private Direction direction;

    // Texte libre, pas obligatoire
    private String application;

    @NotNull(message = "ProcessedFlowType est obligatoire  (MESSAGE, ALERTING ou NOTIFICATION)")
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description est obligatoire")
    private String description;
}
