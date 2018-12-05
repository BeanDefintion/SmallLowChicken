package com.xpj.rabbitmq.springboot;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
//@RabbitListener(queues = "hello")
@RabbitListener(queues = "world")
public class SpringBootReceiveTwo {

    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    @RabbitHandler
    void receive(String hello) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(60);
        System.err.println("Receiver22222  : " + hello);
    }
}
