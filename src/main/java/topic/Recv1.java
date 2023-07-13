package topic;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv1 {

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.130.137.166");
        factory.setUsername("admin");
        factory.setPassword("Xl.010404");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //获取队列
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "order.log.error");



        Consumer consumer = new DefaultConsumer(channel) {


            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {


                System.out.println("body:" + new String(body, "utf-8"));


                //手工确认消息消费，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };


        //消费,关闭消息自动确认，采用手工确认
        channel.basicConsume(queueName, false, consumer);

    }
}