package pub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Recv1 {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.130.137.166");
        factory.setUsername("admin");
        factory.setPassword("Xl.010404");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();



    }
}