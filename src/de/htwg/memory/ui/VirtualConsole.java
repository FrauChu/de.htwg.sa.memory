package de.htwg.memory.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import de.htwg.memory.logic.Util;

public class VirtualConsole {
    private static final class ForKeyWaiter implements KeyListener {
        public static final class Synchronizer {
            private boolean ready = false;
        }

        private final Synchronizer synchronizer = new Synchronizer();

        public void arm() {
            if (synchronizer.ready) {
                synchronizer.ready = false;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            handle(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            e.consume();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            e.consume();
        }

        private void handle(KeyEvent e) {
            synchronized (synchronizer) {
                if (!synchronizer.ready) {
                    synchronizer.ready = true;
                    e.consume();
                    synchronizer.notify();
                }
            }
        }
    }

    private JFrame frame;
    private JTextArea text;
    private JMenu menu;
    private JMenuBar menuBar;
    private ForKeyWaiter k = new ForKeyWaiter();

    public VirtualConsole() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double resolutionFactor = screenSize.getWidth() / 1600;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Virtual Console");
        text = new JTextArea();
        text.addKeyListener(k);
        text.setFont(Util.getOptimalFont());
        frame.add(text, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension((int) (600 * resolutionFactor), (int) (300 * resolutionFactor)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu = new JMenu("Menü");
        menu.setFont(Util.getOptimalFont());
        menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.setFont(Util.getOptimalFont());
        frame.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
        frame.setVisible(true);
    }

    public void kill() {
        for (KeyListener l : text.getKeyListeners()) {
            text.removeKeyListener(l);
        }
        text = null;
        frame.setVisible(false);
        frame = null;
    }

    public void println() {
        text.setText(text.getText() + "\n");
        text.setCaretPosition(text.getDocument().getLength());
    }

    public void println(Object value) {
        print(value);
        println();
    }

    public void print(Object value) {
        text.setText(text.getText() + value.toString());
        text.setCaretPosition(text.getDocument().getLength());
    }

    public String getCurrentText() {
        return text.getText();
    }

    public void clear() {
        text.setText("");
    }

    public void waitForKey() throws InterruptedException {
        KeyListener[] oldListeners = text.getKeyListeners();
        for (KeyListener l : oldListeners) {
            if (l != k) {
                text.removeKeyListener(l);
            }
        }

        k.arm();
        try {
            synchronized (k.synchronizer) {
                while (!k.synchronizer.ready) {
                    k.synchronizer.wait();
                }
            }
        } finally {
            synchronized (k.synchronizer) {
                k.synchronizer.ready = true;
            }
            for (KeyListener l : oldListeners) {
                if (l != k) {
                    text.addKeyListener(l);
                }
            }
        }
    }

    public void addKeyListener(KeyListener l) {
        text.addKeyListener(l);
    }

    public void removeKeyListener(KeyListener l) {
        text.removeKeyListener(l);
    }

    public void addMenueItem(ActionListener a, String s) {
        JMenuItem item = new JMenuItem(s);
        item.setFont(Util.getOptimalFont());
        menu.add(item);
        item.addActionListener(a);
    }
}
