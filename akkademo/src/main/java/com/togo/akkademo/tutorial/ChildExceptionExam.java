package com.togo.akkademo.tutorial;

import java.io.IOException;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ChildExceptionExam {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("testSystem");

		ActorRef firstActor = system.actorOf(FatherActor.props(), "first-actor");
		System.out.println("First : " + firstActor);
		firstActor.tell("failChild", ActorRef.noSender());

		System.out.println(">>> Press ENTER to exit <<<");

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}
}

class FatherActor extends AbstractActor {

	static Props props() {

		return Props.create(FatherActor.class, FatherActor::new);
	}

	ActorRef child = getContext().actorOf(ChildActor.props(), "child-acotr");

	@Override
	public Receive createReceive() {

		return receiveBuilder().matchEquals("failChild", a -> {
			child.tell("fail", getSelf());
		}).build();
	}
}

class ChildActor extends AbstractActor {

	static Props props() {

		return Props.create(ChildActor.class, ChildActor::new);
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("child stop");
	}

	@Override
	public void preStart() throws Exception {

		System.out.println("child start");
	}

	@Override
	public Receive createReceive() {

		return receiveBuilder().matchEquals("fail", c -> {
			System.out.println("child failed");
			throw new RuntimeException("i`m failed");
		}).build();
	}

}