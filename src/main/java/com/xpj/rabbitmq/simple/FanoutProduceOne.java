package com.xpj.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xpj.rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅 发布模式
 * <p>
 * 解读：
 * 1、1个生产者，多个消费者
 * 2、每一个消费者都有自己的一个队列
 * 3、生产者没有将消息直接发送到队列，而是发送到了交换机
 * 4、每个队列都要绑定到交换机
 * 5、生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
 * 注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
 * <p>
 * 所以对于队列而言 只要绑定到了交换机上 都可以得到消息
 * 但对于队列里面的消费者而言 只有1个消费者可以得到消息
 */
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
