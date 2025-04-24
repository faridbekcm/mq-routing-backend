package com.banque.mq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "ibm.mq")
public class MqProperties {

    /**
     * Nom du gestionnaire de files (Queue Manager) IBM MQ.
     */
    private String queueManager;
    /**
     * Nom du canal de communication IBM MQ.
     */
    private String channel;
    /**
     * Liste des hôtes et ports pour accéder à IBM MQ (ex : `host1(1414),host2(1414)`).
     */
    private String connName;
    /**
     * Nom d’utilisateur pour se connecter à IBM MQ.
     */
    private String user;
    /**
     * Mot de passe associé à l’utilisateur IBM MQ.
     */
    private String password;
    /**
     * Nom de la file MQ cible depuis laquelle les messages sont lus.
     */
    private String queue;

    // ------------------ Configuration des timeouts ------------------

    /**
     * Configuration des timeouts pour MQ.
     */
    private Timeout timeout = new Timeout();

    /**
     * Classe interne pour encapsuler la configuration des timeouts.
     */
    @Data
    public static class Timeout {
        /**
         * Timeout de connexion en secondes.
         */
        private int connect;

        /**
         * Timeout de reconnexion après déconnexion.
         */
        private int reconnect;

        /**
         * Timeout de réception en millisecondes.
         */
        private int receive;
    }
}
