package com.xpj.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.xpj.rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TopicReceiveTwo {

    private static final String QUEUE_NAME1 = "test_topic_receive2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        /**
         * 这是把队列绑定到交换机
         * var1 队列名称
         * var2 交换机名称
         * var3 "route-key"/topic
         */
        channel.queueBind(QUEUE_NAME1, TopicProduceOne.EXCHANGE_ROUTE_NAME, "routekey2.*");
        channel.basicQos(1);

        channel.basicConsume(QUEUE_NAME1, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("RTOPIC : in consumer C (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        TimeUnit.MILLISECONDS.sleep(1000 * 600);
        channel.close();
        connection.close();
    }

}
