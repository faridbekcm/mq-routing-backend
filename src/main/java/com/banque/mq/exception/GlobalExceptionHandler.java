package com.banque.mq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions de l'application.
 * Cette classe intercepte toutes les exceptions levées dans les contrôleurs REST
 * et renvoie une réponse HTTP claire, structurée et cohérente pour le client (IHM ou autre).
 *
 * Elle permet de :
 * - Gérer les erreurs de validation de formulaire
 * - Capturer les erreurs de parsing JSON
 * - Distinguer les exceptions fonctionnelles et techniques
 * - Proposer un format standardisé de réponse d'erreur
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les erreurs de validation des arguments annotés avec @Valid.
     * Retourne un message d'erreur clair avec les champs invalides.
     *
     * @param ex l’exception contenant les erreurs de validation
     * @return réponse HTTP 400 avec les détails des champs invalides
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Erreur de validation", errors);
    }

    /**
     * Gère les erreurs liées à un JSON mal formé ou à des types non reconnus dans la requête.
     *
     * @param ex l’exception de parsing JSON
     * @return réponse HTTP 400 avec un message explicite
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadJson(HttpMessageNotReadableException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Format JSON invalide ou valeur non reconnue");
    }

    /**
     * Gère les violations de contraintes provenant des annotations de validation (@NotNull, @Size, etc.).
     *
     * @param ex exception de violation de contrainte
     * @return réponse HTTP 400 avec message détaillé
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolations(ConstraintViolationException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Violation de contrainte : " + ex.getMessage());
    }

    /**
     * Gère les erreurs fonctionnelles déclenchées par la logique métier.
     *
     * @param ex exception fonctionnelle personnalisée
     * @return réponse HTTP 400 avec message métier
     */
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<Object> handleFunctionalException(FunctionalException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Gère les erreurs techniques graves comme les pannes de système, erreurs de persistance, etc.
     *
     * @param ex exception technique personnalisée
     * @return réponse HTTP 500 avec message d’erreur
     */
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Gère toutes les autres exceptions non spécifiquement prises en charge.
     *
     * @param ex exception générique
     * @return réponse HTTP 500 avec message générique
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue.");
    }

    /**
     * Construit une réponse JSON standardisée sans détails supplémentaires.
     *
     * @param status le code HTTP
     * @param message le message d'erreur principal
     * @return une réponse structurée
     */
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        return buildResponseEntity(status, message, null);
    }

    /**
     * Construit une réponse JSON standardisée avec des erreurs détaillées si présentes.
     *
     * @param status le code HTTP
     * @param message le message principal
     * @param errors une map d’erreurs spécifiques, le cas échéant
     * @return un objet de réponse HTTP structuré
     */
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, Map<String, ?> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (errors != null && !errors.isEmpty()) {
            body.put("errors", errors);
        }
        return new ResponseEntity<>(body, status);
    }
}
