package direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String EXCHANGE_NAME = "exchange_direct";

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

            //绑定交换机,直连交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String error = "错误日志ErrorTest";
            String info = "信息日志InfoTest";
            String debug = "调试日志DebugTest";

            channel.basicPublish(EXCHANGE_NAME,  "errorRoutingKey", null,error.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME,  "infoRoutingKey", null,info.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME,  "debugRoutingKey", null,debug.getBytes(StandardCharsets.UTF_8));;

            System.out.println("directSend广播发送成功");

        }
    }
}