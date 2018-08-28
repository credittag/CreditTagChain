package com.browser.tools.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

@Configuration
@EnableScheduling
public class RabbitProducer {
	
	 @Value("${spring.rabbitmq.host}") 
	 private   String HOST;  // = "localhost"; //这里填写你的ip地址
	 @Value("${spring.rabbitmq.port}") 
    private   Integer PORT;
	 @Value("${spring.rabbitmq.username}")
    private   String USERNAME;
	 @Value("${spring.rabbitmq.password}")
    private  String PASSWORD;
	 @Value("${spring.rabbitmq.vhost}")
	private String vhost;

    private static final String EXCHANGE_NAME = "exchange_demo";

    private static final String ROUTING_KEY = "routing_key_demo";

    private static final String QUEUE_NAME = "RISK_ADRESS_QUEUE";
    
    
    public  void producerMsg(String msg) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(vhost);
        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            //channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            
           // channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }
    }
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void test() {
//    	producerMsg("testjjjjjjj");
//    }
    public static void main(String[] args) {
    	//producerMsg("test");
    }

}
