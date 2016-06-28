package de.htwg.memory.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import de.htwg.memory.actor.message.BoardMessage;
import de.htwg.memory.actor.message.SaveMessage;

public class MasterActor extends UntypedActor {
    public static final String ACTOR_NAME = "master";

    private final ActorRef boardActors = getContext().actorOf(Props.create(BoardActor.class), BoardActor.ACTOR_NAME);
    private final ActorRef saveActors = getContext().actorOf(Props.create(SaveActor.class), SaveActor.ACTOR_NAME);

    @Override
    public void onReceive(final Object message) throws Exception {
        if (message instanceof BoardMessage) {
            boardActors.forward(message, getContext());
        } else if (message instanceof SaveMessage) {
        	saveActors.forward(message, getContext());
        } else {
            logUnhandled(ACTOR_NAME, message);
        }
    }

    private final void logUnhandled(String actorName, Object message) {
        System.err.println(actorName + " received an unrecognized message of type: " +
                    message.getClass());
        unhandled(message);
}
}
