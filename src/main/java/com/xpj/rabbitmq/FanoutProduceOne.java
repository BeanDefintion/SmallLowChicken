package com.xpj.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutProduceOne {

    public static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 如果一个exchange的类型被定义为fanout，那么发送到该exchange上的消息会发送给所有监听该exchange的queue中。
         * Fanout类型的exchange不需要处理routingkey，只需要队列监听该exchange就能接收到消息，类似于广播。
         *
         * 发送到类型为direct的exchange的消息，会根据routingkey来判断消息发送给哪个queue。
         * 每个绑定该exchange的queue都需要绑定一个routingkey。如果接收到的消息不符合绑定的每一个routingkey，则消息会被抛弃掉。
         *就是生产者与消费者都需要绑定一个key
         *
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
//        channel.exchangeDeclare(EXCHANGE_NAME, , "direct");

        String message = "你是狗11111111吗? ";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
