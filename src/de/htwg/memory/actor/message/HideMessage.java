package de.htwg.memory.actor.message;

import de.htwg.memory.entities.MemoryCard;

public class HideMessage extends BoardMessage {
	public HideMessage(MemoryCard[][] cards) {
		super(cards);
	}
}
