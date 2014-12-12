package de.htwg.memory.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTextArea;

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
		public void keyReleased(KeyEvent e) { e.consume(); }

		@Override
		public void keyTyped(KeyEvent e) { e.consume(); }

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
	private ForKeyWaiter k = new ForKeyWaiter();

	public VirtualConsole() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("Virtual Console");
		text = new JTextArea();
		text.addKeyListener(k);
		frame.add(text, BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension(600, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void wait(int millis) {
		try {
			Thread.sleep(Long.valueOf(millis));
		} catch (InterruptedException e) {
			Logger.getLogger("").log(Level.ALL, "Interupt exception happend");
		}
	}

	public void waitForKey() {
		KeyListener[] oldListeners = text.getKeyListeners();
		for (KeyListener l : oldListeners) {
			if (l != k) {
				text.removeKeyListener(l);
			}
		}
		
		k.arm();
		synchronized (k.synchronizer) {
			while (!k.synchronizer.ready) {
				try {
					k.synchronizer.wait();
				} catch (InterruptedException e) {
					Logger.getLogger("").log(Level.ALL, "Interupt exception happend");
				}
			}
		}

		for (KeyListener l : oldListeners) {
			if (l != k) {
				text.addKeyListener(l);
			}
		}
	}

	public void addKeyListener(KeyListener l) {
		text.addKeyListener(l);
	}

	public void removeKeyListener(KeyListener l) {
		text.removeKeyListener(l);
	}
}
