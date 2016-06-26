package de.htwg.memory.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.htwg.memory.entities.Board;
import de.htwg.memory.entities.MemoryCard;
import de.htwg.memory.logic.IController;
import de.htwg.memory.logic.UiEventListener;
import de.htwg.memory.logic.Util;
import de.htwg.memory.main.Main;

public class GUI extends JFrame implements UiEventListener {
    private static final long serialVersionUID = -9143205935002378362L;

    private static class HideAfterTimeout implements Runnable {
        public void run() {
            try {
                Thread.sleep(WAIT_TIME_AFTER_WRONG_MATCH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Main.getControllerInstance().hideWrongMatch();
        }
    }

    private static final long WAIT_TIME_AFTER_WRONG_MATCH = 700;

    private IController controller;
    private Board board;
    private int players;

    public GUI() {
        this.controller = Main.getControllerInstance();
        controller.addListener(this);
        this.board = controller.getBoard();
        this.players = controller.getPlayerCount();

        addMenuBar();
        gameReset();

        this.setTitle("Memory");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private final void realoadButtons() {
        JPanel panel = new JPanel(new GridLayout(board.getHeight(), board.getWidth(), 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentPane(panel);
        for (MemoryCard mc : board.getMemoryCardIterator()) {
            this.add(new MemCardButton(mc));
        }
    }

    private final void setWindowSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidthMultiplikator = (screenSize.getWidth() - 25) / board.getWidth();
        double screenHeightMultiplikator = (screenSize.getHeight() - 85) / board.getHeight();
        double multiplikatorToUse = screenWidthMultiplikator < screenHeightMultiplikator ? screenWidthMultiplikator : screenHeightMultiplikator;

        this.setSize((int) Math.round(board.getWidth() * multiplikatorToUse)
                , (int) Math.round(board.getHeight() * multiplikatorToUse));
    }

    private final void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        menu.setFont(Util.getOptimalFont());

        JMenuItem load = new JMenuItem("Load");
        menu.add(load);
        load.addActionListener(new LoadOptionListener());
        load.setFont(Util.getOptimalFont());

        JMenuItem multiplayer = new JMenuItem("Use multiplayer");
        menu.add(multiplayer);
        multiplayer.addActionListener(new MultiplayerOptionListener());
        multiplayer.setFont(Util.getOptimalFont());

        JMenuItem highscore = new JMenuItem("Show highscore");
        menu.add(highscore);
        highscore.addActionListener(new HighscoreOptionListener());
        highscore.setFont(Util.getOptimalFont());

        JMenuItem restart = new JMenuItem("Restart");
        menu.add(restart);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });
        restart.setFont(Util.getOptimalFont());

        JMenuItem exit = new JMenuItem("Exit");
        menu.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit.setFont(Util.getOptimalFont());
        this.setJMenuBar(menuBar);
    }


    @Override
    public void win() {
        StringBuilder text = new StringBuilder();
        text.append("Vitory!");
        if (players == 1) {
            text.append("\nNumber of turns: ").append(controller.getRoundNumber());
        } else {
            for (int i = 0; i < players; i++) {
                text.append("\nMatches Player ").append(i + 1).append(": ").append(controller.getPlayerMatches(i));
            }
        }

        text.append("\nDo you want to restart?");

        if (JOptionPane.showConfirmDialog(this,
                text.toString(),
                "Victory!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            controller.resetGame();
        }
    }

    @Override
    public void matchMade() {
        reload();
    }

    @Override
    public void noMatchMade() {
        reload();
        if (players == 1)
            new Thread(new HideAfterTimeout()).start();
        else {
            JOptionPane.showMessageDialog(this,
                    "It's the turn of player " + controller.getCurrentPlayer(),
                    "Next Player.",
                    JOptionPane.INFORMATION_MESSAGE);
            Main.getControllerInstance().hideWrongMatch();
        }
    }

    @Override
    public void boardNeedsRealod() {
        reload();
    }

    private void reload() {
        invalidate();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                repaint();
                setVisible(true);
            }
        });
    }

    @Override
    public void boardChanged() {
        board = controller.getBoard();
    }

    @Override
    public void playerCountChanged(int players) {
        this.players = players;
    }

    @Override
    public void gameReset() {
        realoadButtons();
        setWindowSize();
    }

	@Override
	public void playerNameChanged(String name) {
		// TODO Auto-generated method stub
		
	}
}
