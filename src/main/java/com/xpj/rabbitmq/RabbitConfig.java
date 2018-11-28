package com.xpj.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.typesafe.config.ConfigFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitConfig {

    private static final String host = ConfigFactory.load().getString("spring.rabbitmq.host");

    private static final String port = ConfigFactory.load().getString("spring.rabbitmq.port");

    private static final String userName = ConfigFactory.load().getString("spring.rabbitmq.username");

    private static final String password = ConfigFactory.load().getString("spring.rabbitmq.password");

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue worldQueue() {
        return new Queue("world");
    }

    @Bean
    public Queue MessageQueue1() {
        return new Queue("topic.message1");
    }


    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.parseInt(port));
        factory.setUsername(userName);
        factory.setPassword(password);

        return factory.newConnection();
    }
}
