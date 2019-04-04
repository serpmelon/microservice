package com.togo.MQServer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class DurabilitySend {

	private static final String QUEUE_NAME = "task_queue";

	public static void main(String[] args) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();) {

			boolean durable = true;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
			String msg = "durable msg";
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
					msg.getBytes());
			System.out.println(" [x] Sent '" + msg + "'");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
