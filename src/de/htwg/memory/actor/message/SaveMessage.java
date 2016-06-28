package de.htwg.memory.actor.message;

import de.htwg.memory.entities.IHighscore;
import de.htwg.memory.persistence.IHighscoreDAO;

public class SaveMessage {
	private IHighscore highscore;
	private IHighscoreDAO dao;
	
	public SaveMessage(IHighscore highscore, IHighscoreDAO dao) {
		this.highscore = highscore;
		this.dao = dao;
	}
	
	public IHighscore getHighscore() {
		return this.highscore;
	}
	
	public IHighscoreDAO getDAO() {
		return this.dao;
	}
}
