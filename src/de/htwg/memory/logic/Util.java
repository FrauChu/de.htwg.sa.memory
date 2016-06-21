package de.htwg.memory.logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public final class Util {

    private Util() {
    }

    public static Image createImageFromString(String s) {
        return createImageFromString(s, Color.BLACK, Color.WHITE);
    }

    public static Image createImageFromString(String s, Color bg, Color fc) {
        BufferedImage i = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = i.createGraphics();
        g.setColor(bg);
        g.fillRect(0, 0, 100, 100);
        g.setColor(fc);
        g.setFont(g.getFont().deriveFont(50f));
        g.drawChars(s.toCharArray(), 0, s.length(), 25, 75);
        return i;
    }

    private static Font optimalFont = null;

    public static Font getOptimalFont() {
        if (optimalFont == null) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double resolutionFactor = screenSize.getWidth() / 1600;
            optimalFont = new Font("Consolas", Font.PLAIN, (int) (16 * resolutionFactor));
        }
        return optimalFont;
    }
}
