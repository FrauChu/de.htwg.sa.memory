package de.htwg.memory.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "highscore")
public class PersistentHighscore implements Serializable {

    private static final long serialVersionUID = 1538704903825440126L;

    @Id
    @Column(name = "id")
    private String id;

    private String Name;
    private int score;

    public PersistentHighscore() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
