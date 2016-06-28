package de.htwg.memory.actor.message;

import de.htwg.memory.entities.MemoryCard;

public class ShuffleMessage extends BoardMessage{
	protected int iterations;

	public ShuffleMessage(MemoryCard[][] cards, int iterations) {
		super(cards);
		this.iterations = iterations;
	}
	
	public int getIterations() {
		return this.iterations;
	}
}
