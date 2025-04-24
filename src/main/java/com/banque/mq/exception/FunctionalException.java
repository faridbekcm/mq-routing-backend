
package com.banque.mq.exception;

/**
 * Exception personnalisée utilisée pour représenter des erreurs fonctionnelles
 * dans le cadre de l’application MQ Routing.
 *
 * Elle est généralement levée lorsqu’un traitement métier ne respecte pas
 * une règle de gestion (par exemple : tentative d’ajouter un partenaire avec un alias déjà existant).
 */
public class FunctionalException extends RuntimeException {

    /**
     * Construit une nouvelle exception fonctionnelle avec le message spécifié.
     *
     * @param message le message d'erreur décrivant la cause fonctionnelle de l'exception
     */
    public FunctionalException(String message) {
        super(message);
    }
}
