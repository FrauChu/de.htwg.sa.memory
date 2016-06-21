package de.htwg.memory.entities;

public interface IMemoryCard {
    /**
     * Is called to set the visibility of a card
     *
     * @param value
     * @return true, if setting on visible succeeded
     */
    boolean setVisible(boolean value);
}
