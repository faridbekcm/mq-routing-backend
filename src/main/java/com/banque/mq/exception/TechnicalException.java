package com.banque.mq.exception;

/**
 * Exception personnalisée utilisée pour signaler des erreurs techniques
 * survenues dans l'application MQ Routing.
 *
 * Elle peut être levée en cas d’erreur système (ex. : problème de connexion à la base de données, file MQ inaccessible, etc.).
 * Contrairement à {@link FunctionalException}, elle ne découle pas d’une règle métier, mais d’un dysfonctionnement technique.
 */
public class TechnicalException extends RuntimeException {

    /**
     * Construit une exception technique avec un message explicite.
     *
     * @param message le message décrivant l’erreur technique
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * Construit une exception technique avec un message et une cause (exception d’origine).
     * Utile pour chaîner les erreurs tout en conservant la trace de l’exception initiale.
     *
     * @param message le message décrivant l’erreur
     * @param cause l’exception technique d’origine
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
