package de.htwg.memory.logic;

import de.htwg.memory.entities.*;

import java.io.File;
import java.util.List;

public interface IController extends MemoryCardEventListener {
    void loadBoard(File gameFile);

    void loadStaticBoard(int width, int height);

    void replaceBoard(Board newBoard);

    void resetGame();

    void hideWrongMatch();

    @Override
    void picked(IMemoryCard mc);

    boolean pickCard(int x, int y);

    boolean processResult(Board.PickResult result);

    Board getBoard();

    int getPlayerCount();

    int getCurrentPlayer();

    void setPlayerCount(int players);

    int getRoundNumber();

    int getPlayerMatches(int playerId);

    void fireWin();

    void fireMatchMade();

    void fireNoMatchMade();

    void fireBoardNeedsReload();

    void fireBoardChanged();

    void firePlayerCountChanged(int players);

    void fireGameReset();

    void addListener(UiEventListener listener);

    void removeListener(UiEventListener listener);

    List<IHighscore> getHighscore();
}
