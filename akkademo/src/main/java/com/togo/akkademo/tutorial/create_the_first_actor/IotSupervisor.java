package com.togo.akkademo.tutorial.create_the_first_actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class IotSupervisor extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public static Props props() {
		return Props.create(IotSupervisor.class, IotSupervisor::new);
	}

	@Override
	public void postStop() throws Exception {
		log.info("IoT APP stopped");
	}

	@Override
	public void preStart() throws Exception {
		log.info("IoT APP started");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().build();
	}

}
