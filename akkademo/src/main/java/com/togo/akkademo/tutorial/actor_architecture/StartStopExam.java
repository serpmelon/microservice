package com.togo.akkademo.tutorial.actor_architecture;

import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class StartStopExam {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("testSystem");

		ActorRef firstActor = system.actorOf(StartStopActor1.props(), "first-actor");
		System.out.println("First : " + firstActor);
		firstActor.tell("stop", ActorRef.noSender());

		System.out.println(">>> Press ENTER to exit <<<");

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}
}

class StartStopActor1 extends AbstractActor {

	static Props props() {

		return Props.create(StartStopActor1.class, StartStopActor1::new);
	}

	@Override
	public void postStop() throws Exception {

		System.out.println("first stop");
	}

	@Override
	public void preStart() throws Exception {

		System.out.println("First started");
		getContext().actorOf(StartStopActor2.props(), "Second");
	}

	@Override
	public Receive createReceive() {

		return receiveBuilder().matchEquals("stop", p -> {
			getContext().stop(getSelf());
		}).build();
	}

}

class StartStopActor2 extends AbstractActor {

	static Props props() {
		return Props.create(StartStopActor2.class, StartStopActor2::new);
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("second stopped");
	}

	@Override
	public void preStart() throws Exception {
		System.out.println("second started");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().build();
	}

}