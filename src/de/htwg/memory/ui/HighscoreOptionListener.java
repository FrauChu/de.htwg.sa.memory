package de.htwg.memory.ui;

import de.htwg.memory.entities.IHighscore;
import de.htwg.memory.main.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HighscoreOptionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        List<IHighscore> highscore = Main.getControllerInstance().getHighscore();
        StringBuilder highscoreText = new StringBuilder();
        for (IHighscore score: highscore) {
            highscoreText.append(score);
            highscoreText.append("\n");
        }

        JOptionPane.showMessageDialog(null, highscoreText, "Highscore", JOptionPane.PLAIN_MESSAGE);
    }
}