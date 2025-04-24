package com.banque.mq.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Objet de transfert représentant un message MQ dans les échanges API.
 */
@Data
@Builder
@Schema(description = "Objet de transfert représentant un message MQ.")
public class MessageDTO {

    @Schema(description = "Contenu brut du message MQ.")
    private String payload;

    @Schema(description = "Source du message (ex. : IBM MQ).")
    private String source;

    @Schema(description = "Date et heure de réception du message.")
    private LocalDateTime receivedAt;

    @Schema(description = "Statut actuel du message (RECEIVED, PROCESSED, etc.).")
    private String status;
}
