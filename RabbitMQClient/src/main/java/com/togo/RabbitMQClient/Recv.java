package com.togo.RabbitMQClient;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * Hello world!
 *
 */
public class Recv {

	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("Hello World!");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		channel.basicQos(1);
		
		DeliverCallback callback = (consumerTag, delivery) -> {
			String msg = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + msg + "'");

			try {
				doWork(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(" [x] Done");
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		};

		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, callback, consumerTag -> {
		});
	}

	private static void doWork(String msg) throws InterruptedException {

		for (char c : msg.toCharArray()) {
			if (c == '.')
				Thread.sleep(5000);
		}
	}
}
