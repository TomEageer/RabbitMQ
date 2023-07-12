package work.rr;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Recv2 {

    private final static String QUEUE_NAME = "work_mq_rr";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.130.137.166");
        factory.setUsername("admin");
        factory.setPassword("Xl.010404");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {


                //模拟消费者消费慢
                try {
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception e){
                    e.printStackTrace();
                }

//                System.out.println("consumerTag:" + consumerTag);
//                System.out.println("envelope:" + envelope);
//                System.out.println("properties:" + properties);
                System.out.println("body:" + new String(body, "utf-8"));


                //手工确认消息消费，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };


        //消费,关闭消息自动确认，采用手工确认
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}