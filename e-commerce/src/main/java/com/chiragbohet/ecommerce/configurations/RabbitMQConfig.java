package com.chiragbohet.ecommerce.configurations;

import com.chiragbohet.ecommerce.listeners.ProductUpdateListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String PRODUCT_UPDATE_QUEUE = "ProductAddUpdateQueue";

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();

        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());   // Connection Details
        simpleMessageListenerContainer.setQueueNames(PRODUCT_UPDATE_QUEUE); // The queue name
        simpleMessageListenerContainer.setMessageListener(new ProductUpdateListener());

        return simpleMessageListenerContainer;
    }

}
