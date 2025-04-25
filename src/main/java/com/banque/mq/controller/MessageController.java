package com.banque.mq.controller;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Contrôleur REST pour l’exposition des endpoints liés aux messages MQ.
 *
 * Permet à l’IHM ou tout autre client :
 * - De consulter les messages MQ avec des filtres dynamiques et pagination
 * - D’accéder au détail d’un message en particulier
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    /**
     * Constructeur avec injection du service {@link MessageService}.
     *
     * @param messageService service métier des messages
     */
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint GET permettant de rechercher les messages MQ de manière paginée,
     * avec filtres optionnels sur la source, le statut et la date de réception.
     *
     * @param page numéro de page (défaut : 0)
     * @param size nombre d’éléments par page (défaut : 10)
     * @param source source du message (facultatif)
     * @param status statut du message (facultatif)
     * @param receivedAt date de réception minimale (facultatif)
     * @return une page de messages MQ sous forme de {@link MessageDTO}
     */
    @Operation(
            summary = "Lister les messages MQ avec filtres et pagination",
            description = "Retourne une liste paginée des messages MQ. "
                    + "Possibilité de filtrer par source, statut et date de réception (ex. : depuis le 01/01/2024)."
    )
    @GetMapping
    public Page<MessageDTO> getAllMessages(
            @Parameter(description = "Numéro de page", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Taille de la page (nombre de résultats)", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Filtrer par source (ex : IBM MQ)")
            @RequestParam(required = false) String source,

            @Parameter(description = "Filtrer par statut du message (ex : RECEIVED)")
            @RequestParam(required = false) String status,

            @Parameter(description = "Filtrer par date de réception (format ISO)", example = "2024-01-01T00:00:00")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime receivedAt
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return messageService.getFilteredMessages(pageable, source, status, receivedAt);
    }

    /**
     * Endpoint GET permettant de récupérer un message unique à partir de son identifiant.
     *
     * @param id identifiant du message recherché
     * @return le message correspondant, ou null s’il n’est pas trouvé
     */
    @Operation(
            summary = "Récupérer un message MQ par son ID",
            description = "Retourne le message correspondant à l’identifiant fourni, sous forme de DTO."
    )
    @GetMapping("/{id}")
    public MessageDTO getMessageById(
            @Parameter(description = "Identifiant unique du message", example = "1")
            @PathVariable Long id
    ) {
        return messageService.getMessageById(id);
    }
}
