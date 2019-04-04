package com.togo.akkademo.tutorial;

import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.setup.ActorSystemSetup;

public class ActorHierarchyExperiments {

	public static void main(String[] args) {

		ActorSystem system = ActorSystem.create("testSystem");

		ActorRef firstActor = system.actorOf(PrintMyActorRefActor.props(), "first-actor");
		System.out.println("First : " + firstActor);
		firstActor.tell("printit", ActorRef.noSender());

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

class PrintMyActorRefActor extends AbstractActor {

	static Props props() {

		return Props.create(PrintMyActorRefActor.class, PrintMyActorRefActor::new);
	}

	@Override
	public Receive createReceive() {

		return receiveBuilder().matchEquals("printit", p -> {
			ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
			System.out.println("second: " + secondRef);
		}).build();
	}

}