package de.htwg.memory.actor;

import akka.actor.UntypedActor;
import de.htwg.memory.actor.message.SaveMessage;

public class SaveActor extends UntypedActor {
    public static final String ACTOR_NAME = "save";

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof SaveMessage) {
			save((SaveMessage) message);
            getSender().tell(message, getSelf());
        } else {
            System.err.println(ACTOR_NAME + "recived unhandled message.");
            getSender().tell(false, getSelf());
    	}
	}

	private void save(SaveMessage message) {
		message.getDAO().saveHighscore(message.getHighscore());
	}
}
