package de.htwg.memory.actor;

import java.util.Random;

import de.htwg.memory.actor.message.HideMessage;
import de.htwg.memory.actor.message.ResetMessage;
import de.htwg.memory.actor.message.ShuffleMessage;
import de.htwg.memory.entities.MemoryCard;
import akka.actor.UntypedActor;

public class BoardActor extends UntypedActor {
    public static final String ACTOR_NAME = "board";

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof ShuffleMessage) {
        	shuffle((ShuffleMessage) message);
            getSender().tell(message, getSelf());
        } else if (message instanceof HideMessage) {
        	hide((HideMessage) message);
            getSender().tell(message, getSelf());
        } else if (message instanceof ResetMessage) {
        	reset((ResetMessage) message);
            getSender().tell(message, getSelf());
        } else {
            System.err.println(ACTOR_NAME + "recived unhandled message.");
            getSender().tell(false, getSelf());
    	}
	}

	private void shuffle(ShuffleMessage message) {
        Random r = new Random();
        MemoryCard[][] cards = message.getCards();
        for (int i = 0; i < message.getIterations(); i++) {
            int x1 = r.nextInt(cards[0].length);
            int y1 = r.nextInt(cards.length);
            int x2 = r.nextInt(cards[0].length);
            int y2 = r.nextInt(cards.length);

            MemoryCard temp = cards[y1][x1];
            cards[y1][x1] = cards[y2][x2];
            cards[y2][x2] = temp;
        }
	}

	private void reset(ResetMessage message) {
        MemoryCard[][] cards = message.getCards();
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
            	if (cards[i][j] != null) {
	            	cards[i][j].setVisible(false);
	            	cards[i][j].setSolved(false);
            	}
            }
        }
	}

	private void hide(HideMessage message) {
        MemoryCard[][] cards = message.getCards();
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
            	if (cards[i][j] != null)
            		cards[i][j].setVisible(false);
            }
        }
	}
}
