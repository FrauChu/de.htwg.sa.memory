package de.htwg.memory.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.inject.Inject;
import de.htwg.memory.entities.*;
import de.htwg.memory.entities.Board.PickResult;
import de.htwg.memory.persistence.IHighscoreDAO;

public class Controller implements IController {
    private Board board;
    private int countRounds;
    private int players;
    private int matchPerPlayer[];
    private boolean waitingForHide;
    private IHighscoreDAO highscoreDAO;

    private LinkedList<UiEventListener> eventListeners;

    @Inject
    public Controller(IHighscoreDAO highscoreDAO) {
        eventListeners = new LinkedList<>();
        board = null;
        countRounds = 0;
        players = 1;
        matchPerPlayer = new int[]{0};
        waitingForHide = false;
        this.highscoreDAO = highscoreDAO;
    }

    @Override
    public void loadBoard(File gameFile) {
        SaveFile sv = new SaveFile(gameFile);

        SettingUtil.setNumberOfCardsToMatch(sv.getCardsToMatch());

        MemoryCard[] memCard = new MemoryCard[sv.getCards().size()];
        BufferedImage image = null;
        int i = 0;
        for (Entry<Integer, String> card : sv.getCards().entrySet()) {
            try {
                image = ImageIO.read(new File(card.getValue()));
                memCard[i++] = new MemoryCard(card.getKey(), image);
            } catch (IOException e) {
                System.err.println("Bilder konnten nicht eingelesen werden! \"" + card.getValue() + "\"");
            }
        }

        replaceBoard(new Board(memCard, sv.getBoardSize().width, sv.getBoardSize().height));
    }

    @Override
    public void loadStaticBoard(int width, int height) {
        replaceBoard(new Board(null, width, height));
    }

    @Override
    public void replaceBoard(Board newBoard) {
        board = newBoard;

        fireBoardChanged();
        resetGame();
    }

    @Override
    public void resetGame() {
        countRounds = 0;
        matchPerPlayer = new int[this.players];
        board.reset();
        board.shuffle();

        fireGameReset();
        fireBoardNeedsReload();
    }

    @Override
    public void hideWrongMatch() {
        if (waitingForHide) {
            board.hideAll();
            fireBoardNeedsReload();
            waitingForHide = false;
        }
    }

    @Override
    public synchronized void picked(IMemoryCard mc) {
        if (!waitingForHide)
            processResult(board.pickCard(mc));
    }

    /**
     * Tries to pick a card and returns if the pick was valid.
     *
     * @param x X-Coordinate of the card
     * @param y Y-Coordinate of the card
     * @return False if the choices is invalid, true otherwise.
     */
    @Override
    public synchronized boolean pickCard(int x, int y) {
        if (waitingForHide || x >= board.getHeight() || y >= board.getWidth())
            return false;
        return processResult(board.pickCard(x, y));
    }

    @Override
    public boolean processResult(PickResult result) {
        if (result == PickResult.INVALID_CARD) {
            return false;
        }

        if (result == PickResult.NO_MATCH_MADE
                || result == PickResult.TOO_MANY_CARDS) {
            countRounds++;
            waitingForHide = true;
            fireNoMatchMade();
        } else if (result == PickResult.TOO_LESS_CARDS) {
            fireBoardNeedsReload();
        } else if (result == PickResult.MATCH_MADE) {
            matchPerPlayer[countRounds % players]++;
            if (board.isFinished()) {
                fireWin();
            } else {
                fireMatchMade();
            }
        }

        return true;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public int getPlayerCount() {
        return players;
    }

    @Override
    public int getCurrentPlayer() {
        return (getRoundNumber() % getPlayerCount()) + 1;
    }

    @Override
    public void setPlayerCount(int players) {
        this.players = players;
        firePlayerCountChanged(this.players);
        resetGame();
    }

    /**
     * Returns 0-based round number
     *
     * @return
     */
    @Override
    public int getRoundNumber() {
        return countRounds;
    }

    @Override
    public int getPlayerMatches(int playerId) {
        if (playerId < players)
            return matchPerPlayer[playerId];
        else
            return -1;
    }

    @Override
    public List<IHighscore> getHighscore() {
        List<IHighscore> highscore = this.highscoreDAO.getAllHighscores();
        Collections.sort(highscore);

        return highscore;
    }

    @Override
    public void fireWin() {
        IHighscore hs = new Highscore();
        //hs.setName();
        hs.setScore(this.countRounds);
        highscoreDAO.saveHighscore(hs);

        for (UiEventListener l : eventListeners) {
            l.win();
        }
    }

    @Override
    public void fireMatchMade() {
        for (UiEventListener l : eventListeners) {
            l.matchMade();
        }
    }

    @Override
    public void fireNoMatchMade() {
        for (UiEventListener l : eventListeners) {
            l.noMatchMade();
        }
    }

    @Override
    public void fireBoardNeedsReload() {
        for (UiEventListener l : eventListeners) {
            l.boardNeedsRealod();
        }
    }

    @Override
    public void fireBoardChanged() {
        for (UiEventListener l : eventListeners) {
            l.boardChanged();
        }
    }

    @Override
    public void firePlayerCountChanged(int players) {
        for (UiEventListener l : eventListeners) {
            l.playerCountChanged(players);
        }
    }

    @Override
    public void fireGameReset() {
        for (UiEventListener l : eventListeners) {
            l.gameReset();
        }
    }

    @Override
    public void addListener(UiEventListener listener) {
        eventListeners.add(listener);
    }

    @Override
    public void removeListener(UiEventListener listener) {
        eventListeners.remove(listener);
    }
}
