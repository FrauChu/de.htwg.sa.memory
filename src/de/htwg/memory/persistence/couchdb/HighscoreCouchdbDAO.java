package de.htwg.memory.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.memory.entities.Highscore;
import de.htwg.memory.entities.IHighscore;
import de.htwg.memory.persistence.IHighscoreDAO;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.Revision;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class HighscoreCouchdbDAO implements IHighscoreDAO {

	private CouchDbConnector db = null;
	
	public HighscoreCouchdbDAO() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url("http://localhost:5984").build();

		} catch (MalformedURLException e) {
		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector("memory_db", true);
		db.createDatabaseIfNotExists();
	}

	private IHighscore copyHighscore(PersistentHighscore phighscore) {
		if(phighscore == null) {
			return null;
		}
		IHighscore highscore = new Highscore();
		highscore.setId(phighscore.getId());
		highscore.setName(phighscore.getName());
		highscore.setScore(phighscore.getScore());

		return highscore;
	}

	private PersistentHighscore copyHighscore(IHighscore highscore) {
		if(highscore == null) {
			return null;
		}

		String highscoreId = highscore.getId();
		PersistentHighscore phighscore;
		if(containsHighscoreById(highscoreId)) {
			// The Object already exists within the session
			phighscore = db.find(PersistentHighscore.class, highscoreId);
		} else {
			// A new database entry
			phighscore = new PersistentHighscore();
		}

		phighscore.setId(highscore.getId());
		phighscore.setName(highscore.getName());
		phighscore.setScore(highscore.getScore());

		return phighscore;
	}

	@Override
	public void saveHighscore(IHighscore highscore) {
		if (containsHighscoreById(highscore.getId())) {
			db.update(copyHighscore(highscore));
		} else {
			db.create(highscore.getId(), copyHighscore(highscore));
		}
	}

	@Override
	public boolean containsHighscoreById(String id) {
		if (getHighscoreById(id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public IHighscore getHighscoreById(String id) {
		PersistentHighscore g = db.find(PersistentHighscore.class, id);
		if (g == null) {
			return null;
		}
		return copyHighscore(g);
	}

	@Override
	public void deleteHighscoreById(String id) {
		db.delete(copyHighscore(getHighscoreById(id)));
	}

	@Override
	public List<IHighscore> getAllHighscores() {
		ViewQuery q = new ViewQuery().allDocs().includeDocs(true);
				
		List<IHighscore> lst = new ArrayList<>();
		for (PersistentHighscore pHighscore : db.queryView(q, PersistentHighscore.class)) {
			lst.add(copyHighscore(pHighscore));
		}

		return lst;
	}
}
