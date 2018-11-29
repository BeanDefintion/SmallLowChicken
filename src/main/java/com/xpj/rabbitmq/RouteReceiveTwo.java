package com.xpj.rabbitmq;

import com.rabbitmq.client.*;
import com.xpj.rabbitmq.RabbitConfig;
import com.xpj.rabbitmq.RouteProduceOne;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式的接收者1
 */
public class RouteReceiveTwo {

    private static final String QUEUE_NAME1 = "test_route_receive2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        channel.queueBind(QUEUE_NAME1, RouteProduceOne.EXCHANGE_ROUTE_NAME, "search");

        channel.basicConsume(QUEUE_NAME1, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("ROUTE : in consumer C (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
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