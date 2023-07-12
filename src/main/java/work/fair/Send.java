package work.fair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private    final static String  QUEUE_NAME = "work_mq_fair";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.130.137.166");
        factory.setUsername("admin");
        factory.setPassword("Xl.010404");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        //JDK7语法自动关闭创建连接
        try (Connection connection = factory.newConnection();
             //创建信道
             Channel channel = connection.createChannel()) {
            /**
             * 队列名称
             * 持久化配置：mq重启后还在
             * 是否独占：只能有⼀个消费者监听队列；当connection关闭是否删除队列，⼀般是false，发布订阅是独占
             * ⾃动删除: 当没有消费者的时候，⾃动删除掉，⼀般是false
             * 其他参数
             * 队列不存在则会⾃动创建，如果存在则不会覆盖，所以此时的时候需要注意属性
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 10; i++){
                String message = "消息发送测试i:" + i;

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

                System.out.println(" [x] Sent '" + message + "'");
            }



        }
    }
}