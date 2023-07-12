package pub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.130.137.166");
        factory.setUsername("admin");
        factory.setPassword("Xl.010404");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        //JDK7语法，自动 关闭，创建链接
        try (Connection connection = factory.newConnection();
             //创建信道
             Channel channel = connection.createChannel()) {

            //绑定交换机fanout扇形，即广播
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String msg = "RabbitMQ发布";
            channel.basicPublish(EXCHANGE_NAME,  "", null,msg.getBytes(StandardCharsets.UTF_8));

            System.out.println("广播发送成功");

        }
    }
}