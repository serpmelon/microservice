package com.togo.akkademo.tutorial.working_with_device_actors;

import java.util.Optional;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DeviceActor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	final String groupId;
	final String deviceId;

	public DeviceActor(String groupId, String deviceId) {

		this.deviceId = deviceId;
		this.groupId = groupId;
	}

	public static Props props(String groupId, String deviceId) {

		return Props.create(DeviceActor.class, () -> new DeviceActor(groupId, deviceId));
	}

	@Override
	public void postStop() throws Exception {
		log.info("Device Actor {}-{} stopped", groupId, deviceId);
	}

	@Override
	public void preStart() throws Exception {
		log.info("Device Actor {}-{} started", groupId, deviceId);
	}

	public static final class ReadTemperature {

		final long requestId;

		public ReadTemperature(long requestId) {

			this.requestId = requestId;
		}
	}

	public static final class ResponseTemperature {

		final long requestId;
		final Optional<Double> value;

		public ResponseTemperature(long requestId, Optional<Double> value) {

			this.requestId = requestId;
			this.value = value;
		}
	}

	public static final class RecordTemperature {

		final long requestId;
		final double value;

		public RecordTemperature(long requestId, double value) {
			this.requestId = requestId;
			this.value = value;
		}
	}

	public static final class TemperatureRecorded {
		final long requestId;

		public TemperatureRecorded(long requestId) {
			this.requestId = requestId;
		}
	}

	Optional<Double> lastTemperatureReading = Optional.empty();

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(ReadTemperature.class, r -> {
			getSender().tell(new ResponseTemperature(r.requestId, lastTemperatureReading),
					getSelf());
		}).match(RecordTemperature.class, r -> {
			log.info("Recorded temperature reading {} with {}", r.value, r.requestId);
			lastTemperatureReading = Optional.of(r.value);
			getSender().tell(new TemperatureRecorded(r.requestId), getSelf());
		}).build();
	}
}
