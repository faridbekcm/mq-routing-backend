package com.banque.mq.model.dto;

import com.banque.mq.model.entity.PartnerEntity.Direction;
import com.banque.mq.model.entity.PartnerEntity.ProcessedFlowType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO représentant un partenaire MQ dans les échanges API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Objet de transfert représentant un partenaire MQ configuré dans l'application.")
public class PartnerDTO {

    @NotBlank(message = "Alias est obligatoire ")
    @Schema(description = "Alias unique du partenaire MQ.", example = "PARTNER_XYZ", required = true)
    private String alias;

    @NotBlank(message = "Type est obligatoire ")
    @Schema(description = "Type de partenaire (ex : SYSTEME, APPLICATION).", example = "SYSTEME", required = true)
    private String type;

    @NotNull(message = "Direction est obligatoire  (INBOUND or OUTBOUND)")
    @Schema(description = "Direction du flux : INBOUND (entrant) ou OUTBOUND (sortant).", example = "INBOUND", required = true)
    private Direction direction;

    @Schema(description = "Nom de l'application associée (champ libre, optionnel).", example = "CoreBankingSystem")
    private String application;

    @NotNull(message = "ProcessedFlowType est obligatoire  (MESSAGE, ALERTING ou NOTIFICATION)")
    @Schema(description = "Type de flux : MESSAGE, ALERTING ou NOTIFICATION.", example = "MESSAGE", required = true)
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description est obligatoire")
    @Schema(description = "Description fonctionnelle du partenaire.", example = "Partenaire principal pour les alertes de paiement.", required = true)
    private String description;
}
