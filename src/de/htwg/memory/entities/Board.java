package de.htwg.memory.entities;

import java.util.ArrayList;
import java.util.Arrays;

import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.pattern.Patterns;
import de.htwg.memory.actor.ActorFactory;
import de.htwg.memory.actor.message.HideMessage;
import de.htwg.memory.actor.message.ResetMessage;
import de.htwg.memory.actor.message.ShuffleMessage;
import de.htwg.memory.logic.SettingUtil;
import de.htwg.memory.main.Main;

public class Board {
    public enum PickResult {
        NO_MATCH_MADE,
        MATCH_MADE,
        TOO_LESS_CARDS,
        TOO_MANY_CARDS,
        INVALID_CARD
    }

    private MemoryCard[][] memoryCards;
    private int cardCount;

    public Board(MemoryCard[] memoryCards, int width, int height) {
        MemoryCard[] cards = memoryCards;
        this.memoryCards = new MemoryCard[height][width];
        cardCount = 0;

        if (cards == null) {
            cards = new MemoryCard[width * height / SettingUtil.getNumberOfCardsToMatch()];
            for (int i = 0; i < width * height / SettingUtil.getNumberOfCardsToMatch(); i++) {
                cards[i] = new MemoryCard(i + 1);
            }
        }

        for (int i = 0; i < width * height / SettingUtil.getNumberOfCardsToMatch() && i < cards.length; i++) {
            addCardPair(cards[i]);
        }
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getSelectedCardCount() {
        return getChoosenCards().length;
    }

    public int getWidth() {
        return memoryCards[0].length;
    }

    public int getHeight() {
        return memoryCards.length;
    }

    public final void addCardPair(MemoryCard card) {
        if (card == null) {
            throw new NullPointerException("Can't add null Memory Cards");
        }
        if (cardCount + SettingUtil.getNumberOfCardsToMatch() > getWidth() * getHeight()) {
            throw new IndexOutOfBoundsException("Board has reached maximum number of cards");
        }
        for (int i = 0; i < SettingUtil.getNumberOfCardsToMatch(); i++) {
            this.memoryCards[cardCount / getWidth()][cardCount % getWidth()] = card.clone();
            this.memoryCards[cardCount / getWidth()][cardCount % getWidth()].addListener(Main.getControllerInstance());
            cardCount++;
        }
    }

    public PickResult pickCard(int x, int y) {
        if (memoryCards[x][y] == null)
            return PickResult.INVALID_CARD;
        return pickCard(memoryCards[x][y]);
    }

    public PickResult pickCard(IMemoryCard c) {
        if (!c.setVisible(true)) {
            return PickResult.INVALID_CARD;
        }

        return updateMatchings();
    }

    public boolean hasVisibleCard() {
        boolean hasVisible = false;
        for (int i = 0; i < memoryCards.length; i++) {
            for (int j = 0; j < memoryCards[i].length; j++) {
                hasVisible |= getCard(i, j).isVisible() && !getCard(i, j).isSolved();
            }
        }
        return hasVisible;
    }

    public boolean isFinished() {
        boolean isFinished = true;
        for (int i = 0; i < memoryCards.length && isFinished; i++) {
            for (int j = 0; j < memoryCards[i].length && isFinished; j++) {
                if (memoryCards[i][j] != null)
                    isFinished &= getCard(i, j).isSolved();
            }
        }
        return isFinished;
    }

    private MemoryCard getCard(int x, int y) {
        if (memoryCards[x][y] != null) {
            return memoryCards[x][y];
        } else {
            return new MemoryCard();
        }
    }

    public PickResult updateMatchings() {
        MemoryCard foundCards[] = getChoosenCards();

        if (foundCards.length < SettingUtil.getNumberOfCardsToMatch()) {
            return PickResult.TOO_LESS_CARDS;
        } else if (foundCards.length > SettingUtil.getNumberOfCardsToMatch()) {
            return PickResult.TOO_MANY_CARDS;
        }

        boolean allMatch = true;
        for (int i = 1; i < foundCards.length; i++) {
            allMatch &= foundCards[0].compareTo(foundCards[i]) == 0;
        }
        if (allMatch) {
            for (int i = 0; i < foundCards.length; i++) {
                foundCards[i].setSolved(true);
            }
            return PickResult.MATCH_MADE;
        }

        return PickResult.NO_MATCH_MADE;
    }

    private MemoryCard[] getChoosenCards() {
        MemoryCard foundCards[] = new MemoryCard[getWidth() * getHeight()];
        int cardsFound = 0;

        for (int i = 0; i < memoryCards.length; i++) {
            for (int j = 0; j < memoryCards[i].length; j++) {
                if (getCard(i, j).isVisible() && !getCard(i, j).isSolved()) {
                    foundCards[cardsFound++] = getCard(i, j);
                }
            }
        }

        MemoryCard result[] = new MemoryCard[cardsFound];
        for (int i = 0; i < cardsFound; i++) {
            result[i] = foundCards[i];
        }
        return result;
    }

    public void shuffle() {
        shuffle(200);
    }

    public void shuffle(int shuffleCount) {
    	ShuffleMessage sm = new ShuffleMessage(memoryCards, shuffleCount);
    	Future<Object> fut = Patterns.ask(ActorFactory.getMasterRef(), sm, SettingUtil.getTimeout());
    	try {
    		Await.result(fut, SettingUtil.getTimeout().duration());
    	} catch (Exception e) {
            e.printStackTrace();
		}
    }

    public void hideAll() {
    	HideMessage hm = new HideMessage(memoryCards);
    	Future<Object> fut = Patterns.ask(ActorFactory.getMasterRef(), hm, SettingUtil.getTimeout());
    	try {
    		Await.result(fut, SettingUtil.getTimeout().duration());
    	} catch (Exception e) {
            e.printStackTrace();
		}
    }

    public void reset() {
    	ResetMessage rm = new ResetMessage(memoryCards);
    	Future<Object> fut = Patterns.ask(ActorFactory.getMasterRef(), rm, SettingUtil.getTimeout());
    	try {
    		Await.result(fut, SettingUtil.getTimeout().duration());
    	} catch (Exception e) {
            e.printStackTrace();
		}
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (memoryCards[i][j] != null)
                    sb.append(memoryCards[i][j]).append(" ");
                else
                    sb.append(" X  ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Iterable<MemoryCard> getMemoryCardIterator() {
        ArrayList<MemoryCard> forIt = new ArrayList<>();
        for (int i = 0; i < memoryCards.length; i++) {
            forIt.addAll(Arrays.asList(memoryCards[i]));
        }
        return forIt;
    }
}
