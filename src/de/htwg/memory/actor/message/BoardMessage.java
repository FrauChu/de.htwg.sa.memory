package de.htwg.memory.actor.message;

import de.htwg.memory.entities.MemoryCard;

public class BoardMessage {
	protected MemoryCard[][] cards;

	public BoardMessage(MemoryCard[][] cards) {
		this.cards = cards;
	}
	
	public MemoryCard[][] getCards() {
		return this.cards;
	}

}
