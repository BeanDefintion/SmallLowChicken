package com.xpj.rabbitmq.springboot;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpringBootSender {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send() {
        String context = LocalDateTime.now().toString() + " Hello";
        rabbitmqTemplate.convertAndSend("hello", context);
    }


    public void send1() {
        String context = "hi, i am message 11111-----1.2";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("top_exchange_springboot", "1.2", context);
    }


    public void send2() {
        String context = "hi, i am messages 22222222222------3.2";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("top_exchange_springboot", "3.2", context);
    }

}


