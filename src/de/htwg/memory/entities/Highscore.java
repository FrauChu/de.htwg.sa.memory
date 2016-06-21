package de.htwg.memory.entities;

/**
 * Created by simon on 21.06.16.
 */
public class Highscore implements IHighscore {
    private String id;
    private String Name;
    private int score;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        Name = name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
}
