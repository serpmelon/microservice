package com.togo.MQServer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) {
		System.out.println("Hello World!");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String msg = String.join("", args);
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println(" [x] Sent '" + msg + "'");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
