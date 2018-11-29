package com.xpj.rabbitmq.route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xpj.rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由器模式的生产者
 * <p>
 * 路由模式是通过route-key来进行绑定的
 * 消费者在把队列绑定到交换机的过程中如果routekey不匹配的话是收不到消息的
 */
public class RouteProduceOne {

    public static final String EXCHANGE_ROUTE_NAME = "test_exchange_route_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 创建交换机 之前是直接创建队列 利用队列来进行消息通信的
         */
        channel.exchangeDeclare(EXCHANGE_ROUTE_NAME, "direct");

        String message = "嘿嘿 你来打我呀";
        channel.basicPublish(EXCHANGE_ROUTE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

}
