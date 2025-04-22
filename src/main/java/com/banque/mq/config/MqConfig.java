package com.banque.mq.config;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class MqConfig {

    private final MqProperties props;

    public MqConfig(MqProperties props) {
        this.props = props;
    }

    @Bean
    public ConnectionFactory mqConnectionFactory() throws Exception {
        MQConnectionFactory mqFactory = new MQConnectionFactory();
        mqFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        mqFactory.setQueueManager(props.getQueueManager());
        mqFactory.setChannel(props.getChannel());
        mqFactory.setConnectionNameList(props.getConnName());

        mqFactory.setIntProperty(WMQConstants.WMQ_CLIENT_RECONNECT_TIMEOUT, props.getTimeoutReconnect());
        mqFactory.setIntProperty(WMQConstants.WMQ_CLIENT_RECONNECT_OPTIONS, WMQConstants.WMQ_CLIENT_RECONNECT);

        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setUsername(props.getUser());
        adapter.setPassword(props.getPassword());
        adapter.setTargetConnectionFactory(mqFactory);

        return adapter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory); // utilise les propriétés du application.yml
        return factory;
    }
}