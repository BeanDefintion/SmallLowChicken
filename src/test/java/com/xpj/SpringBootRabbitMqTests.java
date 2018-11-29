package com.xpj;

import com.xpj.rabbitmq.springboot.SpringBootSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * 必须写在test目录下才会有test对应的注解
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitMqTests {

    @Autowired
    private SpringBootSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }


    @Test
    public void multiHello() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            helloSender.send();
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }

    @Test
    public void multiHello1() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            helloSender.send1();
            helloSender.send2();
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
