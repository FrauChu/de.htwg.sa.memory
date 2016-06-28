package de.htwg.memory.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorFactory {
    private static final String SYSTEM_NAME = "Memory_Actor_System";
    private static ActorSystem actorSystem = ActorSystem.create(SYSTEM_NAME);
    private static ActorRef master = actorSystem.actorOf(Props.create(MasterActor.class), MasterActor.ACTOR_NAME);

    public static ActorRef getMasterRef() {
        return master;
    }

    public static ActorSystem getActorSystem() {
        return actorSystem;
}
}
