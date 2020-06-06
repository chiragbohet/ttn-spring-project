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
        simpleMessageListenerContainer.setMessageListener(getListener());

        return simpleMessageListenerContainer;
    }

    // Not having this and explicitly creating listener object using new was causing issues with @Autowire not working inside listener, now its working fine.
    // Ref : https://stackoverflow.com/questions/32937547/spring-autowiring-not-working-for-rabbitlistenercontainer#
    @Bean
    public ProductUpdateListener getListener() {
        return new ProductUpdateListener();
    }
}
