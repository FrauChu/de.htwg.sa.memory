package de.htwg.memory.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.htwg.memory.entities.Board;
import de.htwg.memory.logic.IController;
import de.htwg.memory.logic.UiEventListener;
import de.htwg.memory.main.Main;

public class TUI implements UiEventListener, KeyListener {
    private VirtualConsole virtualConsole;
    private Board board;
    private IController controller;
    private int players;
    private int selectedRow;
    private boolean waitingForKey;

    public TUI() {
        this.controller = Main.getControllerInstance();
        gameReset();

        virtualConsole = new VirtualConsole();
        virtualConsole.addKeyListener(this);
        virtualConsole.addMenueItem(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        }, "Reset");
        virtualConsole.addMenueItem(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }, "Exit");

        this.controller.addListener(this);
        this.board = controller.getBoard();
        this.players = controller.getPlayerCount();
        boardNeedsRealod();
    }

    private void printMessage(String message) {
        virtualConsole.clear();
        virtualConsole.println(board);
        virtualConsole.println(message);
    }

    private void handleInput(char keyChar) {
        if (waitingForKey) {
            controller.hideWrongMatch();
            return;
        }
        int key;
        try {
            key = Integer.valueOf(String.valueOf(keyChar)) - 1;
        } catch (NumberFormatException e) {
            if (keyChar == KeyEvent.VK_BACK_SPACE) {
                if (roleBack()) {
                    return;
                }
            }
            virtualConsole.println("Invalid input: '" + keyChar + "'");
            return;
        }

        handleIndex(key);
    }

    private boolean handleIndex(int index) {
        if (index >= 0 && (
                (selectedRow != -1 && index < board.getWidth())
                        || (selectedRow == -1 && index < board.getHeight())
        )) {
            virtualConsole.print(index + 1);
            if (selectedRow == -1) {
                selectedRow = index;
                return false;
            }

            if (!controller.pickCard(selectedRow, index)) {
                virtualConsole.println();
                virtualConsole.println("Invalid selection: '" + String.valueOf(selectedRow + 1) + (index + 1) + "'");
                selectedRow = -1;
                return false;
            }
            selectedRow = -1;
        } else {
            virtualConsole.println();
            virtualConsole.println("Index out of range: '" + (index + 1) + "'");
            return false;
        }
        return true;
    }

    private boolean roleBack() {
        if (selectedRow == -1) {
            return false;
        }
        selectedRow = -1;
        return true;
    }

    @Override
    public void win() {
        printMessage("Congratulation, you won!");
        virtualConsole.println("Rounds played: " + controller.getRoundNumber());
    }

    @Override
    public void matchMade() {
        printMessage("Correct match!");
    }

    @Override
    public void noMatchMade() {
        printMessage("No match! Press any key to hide and continue... ");
        waitingForKey = true;
    }

    @Override
    public void boardNeedsRealod() {
        waitingForKey = false;
        String prefix = "S";
        if (players != 1) {
            prefix = "Player " + (controller.getRoundNumber() % players + 1) + ", please s";
        }
        printMessage(prefix + "elect card " + (board.getSelectedCardCount() + 1) + ":");
    }

    @Override
    public void boardChanged() {
        this.board = controller.getBoard();
    }

    @Override
    public void playerCountChanged(int players) {
        this.players = players;
    }

    @Override
    public void gameReset() {
        this.selectedRow = -1;
        this.waitingForKey = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        handleInput(e.getKeyChar());
        e.consume();
    }

	@Override
	public void playerNameChanged(String name) {
		// TODO Auto-generated method stub
		
	}
}
