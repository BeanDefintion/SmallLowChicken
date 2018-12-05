package com.xpj.rabbitmq.springboot;

import io.micrometer.core.instrument.util.TimeUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
//@RabbitListener(queues = "hello")
@RabbitListener(queues = "topic.message1")
public class SpringBootReceiveOne {

    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    @RabbitHandler
    void receive(String hello) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(20);
        System.err.println("Receiver  : " + hello);
    }
}
