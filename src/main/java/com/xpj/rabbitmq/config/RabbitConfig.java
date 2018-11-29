package com.xpj.rabbitmq.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.typesafe.config.ConfigFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ的基本配置
 */
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

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("top_exchange_springboot");
    }

    /**
     * 绑定交换机与队列
     * with 是""的话就是所有的都能收到
     *
     * @return
     */
    @Bean
    public Binding bindExchangeMessageAll() {
        return BindingBuilder.bind(worldQueue()).to(topicExchange()).with("2.*");
    }

    @Bean
    public Binding bindExchangeMessage1() {
        return BindingBuilder.bind(MessageQueue1()).to(topicExchange()).with("1.*");
    }

    /**
     * 获取一个RabbitMQ的Connection实例
     *
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.parseInt(port));
        factory.setUsername(userName);
        factory.setPassword(password);

        return factory.newConnection();
    }
}
