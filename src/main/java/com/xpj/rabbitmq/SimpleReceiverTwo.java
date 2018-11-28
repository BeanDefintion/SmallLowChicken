package com.xpj.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleReceiverTwo {
    private static final String QUEUE_NAME = "xpj_test_01";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        channel.queueBind(QUEUE_NAME, FanoutProduceOne.EXCHANGE_NAME, "");
        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("in consumer B (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
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
