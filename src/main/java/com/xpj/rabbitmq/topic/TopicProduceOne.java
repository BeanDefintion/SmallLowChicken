package com.xpj.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xpj.rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题匹配模式的生产者
 */
public class TopicProduceOne {

    public static final String EXCHANGE_ROUTE_NAME = "test_exchange_topic_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 创建交换机 之前是直接创建队列 利用队列来进行消息通信的
         */
        channel.exchangeDeclare(EXCHANGE_ROUTE_NAME, "topic");

        String message = "嘿嘿 你来打死我呀1";
        String message2 = "嘿嘿 你来打死我呀2";
        String message3 = "嘿嘿 你来打死我呀3";

        channel.basicPublish(EXCHANGE_ROUTE_NAME, "routekey1.123", null, message.getBytes());
        channel.basicPublish(EXCHANGE_ROUTE_NAME, "routekey2.123", null, message2.getBytes());
        channel.basicPublish(EXCHANGE_ROUTE_NAME, "routekey3.123", null, message3.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }
}
