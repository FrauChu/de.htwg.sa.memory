package de.htwg.memory.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.htwg.memory.entities.MemoryCard;

public class MemCardButton extends JButton implements ActionListener {
    private static final long serialVersionUID = 446045571906652708L;

    MemoryCard card;

    public MemCardButton(MemoryCard card) {
        super();
        this.card = card;
        this.addActionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        if (card == null || card.getPicture() == null) {
            g.setColor(Color.WHITE);
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            g.drawLine(0, 0, this.getWidth(), this.getHeight());
            g.drawLine(0, this.getHeight(), this.getWidth(), 0);
            return;
        }
        g.setColor(Color.RED);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(card.getPicture(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        card.pick();
    }
}
