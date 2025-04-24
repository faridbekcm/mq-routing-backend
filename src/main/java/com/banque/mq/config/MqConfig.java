package com.banque.mq.config;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

/**
 * Configuration des connexions JMS vers IBM MQ.
 * Cette classe définit les beans nécessaires pour que l’application puisse interagir avec IBM MQ :
 * - Création de la connection factory MQ
 * - Création du template JMS pour envoyer des messages
 * - Création du listener container factory pour écouter les files MQ
 */
@Configuration
@EnableJms
public class MqConfig {

    private final MqProperties props;

    /**
     * Injection des propriétés MQ définies dans le fichier de configuration.
     *
     * @param props les propriétés MQ injectées depuis application.yml
     */
    public MqConfig(MqProperties props) {
        this.props = props;
    }

    /**
     * Crée une {@link ConnectionFactory} configurée pour se connecter à IBM MQ
     * en mode client via le protocole JMS.
     * Elle est enrichie avec les informations de reconnexion, canal, credentials, etc.
     *
     * @return une connection factory prête à être utilisée par le template ou les listeners JMS
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public ConnectionFactory mqConnectionFactory() throws Exception {
        MQConnectionFactory mqFactory = new MQConnectionFactory();
        mqFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        mqFactory.setQueueManager(props.getQueueManager());
        mqFactory.setChannel(props.getChannel());
        mqFactory.setConnectionNameList(props.getConnName());

        // Configuration de la résilience
        mqFactory.setIntProperty(WMQConstants.WMQ_CLIENT_RECONNECT_TIMEOUT, props.getTimeout().getReconnect());
        mqFactory.setIntProperty(WMQConstants.WMQ_CLIENT_RECONNECT_OPTIONS, WMQConstants.WMQ_CLIENT_RECONNECT);

        // Ajout des credentials utilisateur
        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(props.getUser());
        adapter.setPassword(props.getPassword());
        adapter.setTargetConnectionFactory(mqFactory);

        return adapter;
    }

    /**
     * Crée un bean {@link JmsTemplate} qui permet d’envoyer des messages à IBM MQ.
     * Ce template repose sur la connection factory définie précédemment.
     *
     * @param connectionFactory la factory de connexion MQ
     * @return le JmsTemplate utilisé dans les couches service
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    /**
     * Crée un container JMS permettant d’écouter les files IBM MQ.
     * Il s’appuie sur la configuration standard définie dans Spring Boot.
     * Permet notamment l'utilisation d'annotations comme @JmsListener.
     *
     * @param connectionFactory la factory de connexion JMS
     * @param configurer le configurateur Spring Boot pour le listener
     * @return un container JMS configuré
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory); // utilise les propriétés du application.yml
        return factory;
    }
}
