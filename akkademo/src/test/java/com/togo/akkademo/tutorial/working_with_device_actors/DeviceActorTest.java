package com.togo.akkademo.tutorial.working_with_device_actors;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.xpath.internal.operations.Div;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Option;
import akka.testkit.javadsl.TestKit;

public class DeviceActorTest {

	static ActorSystem system;

	@BeforeClass
	public static void setup() {

		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {

		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {

		TestKit probe = new TestKit(system);

		ActorRef deviceActor = system.actorOf(DeviceActor.props("group", "device"));
		deviceActor.tell(new DeviceActor.ReadTemperature(42L), probe.getRef());
		DeviceActor.ResponseTemperature response = probe
				.expectMsgClass(DeviceActor.ResponseTemperature.class);

		assertEquals(42L, response.requestId);
		assertEquals(Optional.empty(), response.value);
	}
}
