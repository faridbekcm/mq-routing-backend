package com.banque.mq.controller;

import com.banque.mq.model.dto.PartnerDTO;
import com.banque.mq.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Contrôleur REST pour la gestion des partenaires MQ.
 *
 * Permet à l’IHM de :
 * - Consulter la liste paginée des partenaires configurés
 * - Ajouter un nouveau partenaire via un formulaire
 * - Supprimer un partenaire existant
 */
@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    /**
     * Constructeur avec injection du service métier {@link PartnerService}.
     *
     * @param partnerService service de gestion des partenaires
     */
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /**
     * Endpoint GET pour récupérer une liste paginée des partenaires enregistrés.
     *
     * @param page numéro de page (défaut : 0)
     * @param size taille de la page (défaut : 10)
     * @return une page de {@link PartnerDTO}
     */
    @Operation(
            summary = "Lister les partenaires MQ avec pagination",
            description = "Retourne la liste des partenaires MQ existants dans le système, avec pagination."
    )
    @GetMapping
    public Page<PartnerDTO> getAllPartners(
            @Parameter(description = "Numéro de page", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Taille de la page (nombre de résultats)", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return partnerService.getAll(pageable);
    }

    /**
     * Endpoint POST pour créer un nouveau partenaire MQ.
     *
     * @param dto objet contenant les informations du partenaire à enregistrer
     */
    @Operation(
            summary = "Créer un nouveau partenaire MQ",
            description = "Crée un nouveau partenaire à partir des données envoyées dans la requête (JSON). "
                    + "Tous les champs obligatoires sont validés automatiquement."
    )
    @PostMapping
    public void createPartner(
            @Parameter(description = "Données du partenaire à enregistrer", required = true)
            @Valid @RequestBody PartnerDTO dto
    ) {
        partnerService.save(dto);
    }

    /**
     * Endpoint DELETE pour supprimer un partenaire existant à partir de son identifiant.
     *
     * @param id identifiant unique du partenaire à supprimer
     */
    @Operation(
            summary = "Supprimer un partenaire",
            description = "Supprime un partenaire à partir de son identifiant numérique unique."
    )
    @DeleteMapping("/{id}")
    public void deletePartner(
            @Parameter(description = "Identifiant du partenaire à supprimer", example = "42")
            @PathVariable Long id
    ) {
        partnerService.delete(id);
    }
}
