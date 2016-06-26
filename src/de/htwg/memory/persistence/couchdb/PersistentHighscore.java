package de.htwg.memory.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class PersistentHighscore extends CouchDbDocument {

    private static final long serialVersionUID = 1538704903825440126L;

    @TypeDiscriminator
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
