package de.htwg.memory.actor.message;

import de.htwg.memory.entities.MemoryCard;

public class ResetMessage extends BoardMessage {
	public ResetMessage(MemoryCard[][] cards) {
		super(cards);
	}
}
