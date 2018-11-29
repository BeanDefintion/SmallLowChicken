package com.xpj.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.xpj.rabbitmq.config.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SimpleReceiverOne {

    private static final String QUEUE_NAME = "xpj_test_01";

    private static final String QUEUE_NAME1 = "xpj_test_02";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        /**
         * RabbitMQ 默认将消息顺序发送给下一个消费者，这样，每个消费者会得到相同数量的消息。即轮询（round-robin）分发消息。
         * 怎样才能做到按照每个消费者的能力分配消息呢？联合使用 Qos 和 Acknowledge 就可以做到。
         * basicQos 方法设置了当前信道最大预获取（prefetch）消息数量为1。消息从队列异步推送给消费者，消费者的 ack 也是异步发送给队列，
         * 从队列的视角去看，总是会有一批消息已推送但尚未获得 ack 确认，Qos 的 prefetchCount 参数就是用来限制这批未确认消息数量的。设为1时，
         * 队列只有在收到消费者发回的上一条消息 ack 确认后，才会向该消费者发送下一条消息。prefetchCount 的默认值为0，即没有限制，
         * 队列会将所有消息尽快发给消费者。
         */

        /**
         * 轮询分发 ：使用任务队列的优点之一就是可以轻易的并行工作。如果我们积压了好多工作，我们可以通过增加工作者（消费者）来解决这一问题，
         * 使得系统的伸缩性更加容易。在默认情况下，RabbitMQ将逐个发送消息到在序列中的下一个消费者(而不考虑每个任务的时长等等，且是提前一次性分配，并非一个一个分配)。
         * 平均每个消费者获得相同数量的消息。这种方式分发消息机制称为Round-Robin（轮询）。
         *
         * 公平分发 ：虽然上面的分配法方式也还行，但是有个问题就是：比如：现在有2个消费者，所有的奇数的消息都是繁忙的，而偶数则是轻松的。
         * 按照轮询的方式，奇数的任务交给了第一个消费者，所以一直在忙个不停。偶数的任务交给另一个消费者，则立即完成任务，然后闲得不行。
         * 而RabbitMQ则是不了解这些的。这是因为当消息进入队列，RabbitMQ就会分派消息。它不看消费者为应答的数目，只是盲目的将消息发给轮询指定的消费者。
         *
         * 为了解决这个问题，我们使用basicQos( prefetchCount = 1)方法，来限制RabbitMQ只发不超过1条的消息给同一个消费者。
         * 当消息处理完毕后，有了反馈，才会进行第二次发送。
         * 还有一点需要注意，使用公平分发，必须关闭自动应答，改为手动应答。
         */
        channel.basicQos(1);

        /**
         * 把队列绑定到交换机上面
         */
        channel.queueBind(QUEUE_NAME1, FanoutProduceOne.EXCHANGE_NAME, "");
        /**
         * 构建队列的一个消费者
         * 监听队列，false表示手动返回完成状态，true表示自动
         *
         * 模式1：自动确认
         * 只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费。
         * 模式2：手动确认
         * 消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
         */

        channel.basicConsume(QUEUE_NAME1, false, new DefaultConsumer(channel) {
            /**
             * @param consumerTag 消费者的标志
             * @param envelope 信道 Delivery Tag 用来标识信道中投递的消息 可见，两个信道的 delivery tag 分别从 1 递增到 5。
             *       （如果修改代码，将两个 Consumer 共享同一个信道，则 delivery tag 是从 1 递增到 10
             * @param properties
             * @param body 收到的byte数组
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.printf("in consumer A (delivery tag is %d): %s\n", envelope.getDeliveryTag(), message);
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 消息的确认(是否批量)
                 * basicAck 方法的第二个参数 是否是批量确认
                 * multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认；如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
                 */
                // 返回确认状态，注释掉表示使用自动确认模式
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        /**
         * 测试消息未被消费 依旧留在队列中的情况
         */
//        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
//        channel.basicPublish("", QUEUE_NAME1, null, "你是狗吗1?".getBytes());
//        channel.basicPublish("", QUEUE_NAME1, null, "你是狗吗2?".getBytes());
//        channel.basicPublish("", QUEUE_NAME1, null, "你是狗吗3?".getBytes());

        TimeUnit.MILLISECONDS.sleep(1000 * 600);
        channel.close();
        connection.close();
    }
}
