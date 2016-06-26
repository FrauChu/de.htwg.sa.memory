package de.htwg.memory.logic;


public interface UiEventListener {
    /**
     * Listener handling BoardEvents
     * @author Simon Müller und Sandra Berger
     * @since 13.01.2015
     */

    /**
     * Is called when game is won
     */
    void win();

    /**
     * Is called when player made a card match
     */
    void matchMade();

    /**
     * Is called when no match was made before cards are hidden
     */
    void noMatchMade();

    /**
     * Is called after cards where shown/hidden or board was reset
     */
    void boardNeedsRealod();

    /**
     * Board replaced. UI needs to reset it's reference.
     */
    void boardChanged();

    /**
     * Players added or removed.
     */
    void playerCountChanged(int players);

    /**
     * Called when the game is reset, to also reset all local lvariables.
     */
    void gameReset();

	void playerNameChanged(String name);
}
