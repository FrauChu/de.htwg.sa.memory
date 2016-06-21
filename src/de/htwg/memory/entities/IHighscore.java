package de.htwg.memory.entities;

/**
 * Created by simon on 21.06.16.
 */
public interface IHighscore {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    int getScore();

    void setScore(int score);
}
