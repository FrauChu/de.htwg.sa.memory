package de.htwg.memory.persistence;

import java.util.List;

import de.htwg.memory.entities.IHighscore;

public interface IHighscoreDAO {
	
	void saveHighscore(final IHighscore highscore);

	boolean containsHighscoreById(final String id);
	
	IHighscore getHighscoreById(final String id);

	void deleteHighscoreById(final String id);
	
	List<IHighscore> getAllHighscores();
}