package de.htwg.memory.entities;

public interface IHighscore extends Comparable<IHighscore> {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    int getScore();

    void setScore(int score);
}
