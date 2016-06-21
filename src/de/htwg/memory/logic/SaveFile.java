package de.htwg.memory.logic;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveFile {
    private Dimension boardSize;
    private int cardsToMatch;
    private Map<Integer, String> cards;

    public SaveFile(File file) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            String generalData[] = r.readLine().split("\t");
            boardSize = new Dimension(Integer.parseInt(generalData[0].split("x")[0]), Integer.parseInt(generalData[0].split("x")[1]));
            cardsToMatch = Integer.parseInt(generalData[1]);

            String line;
            cards = new HashMap<>();
            while ((line = r.readLine()) != null) {
                String parts[] = line.split("\t");
                String imageLocation = parts[1];
                if (!imageLocation.contains(":") && !imageLocation.startsWith("/"))
                    imageLocation = file.getParent() + File.separator + imageLocation;
                cards.put(Integer.parseInt(parts[0]), imageLocation);
            }

            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dimension getBoardSize() {
        return boardSize;
    }

    public int getCardsToMatch() {
        return cardsToMatch;
    }

    public Map<Integer, String> getCards() {
        return cards;
    }
}
