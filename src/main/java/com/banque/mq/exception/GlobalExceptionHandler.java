
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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Erreur de validation", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadJson(HttpMessageNotReadableException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Format JSON invalide ou valeur non reconnue");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolations(ConstraintViolationException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Violation de contrainte : " + ex.getMessage());
    }

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<Object> handleFunctionalException(FunctionalException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Toujours garder en dernier pour capturer les exceptions inattendues
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue.");
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        return buildResponseEntity(status, message, null);
    }

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
