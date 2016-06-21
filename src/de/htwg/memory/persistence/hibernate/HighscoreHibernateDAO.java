package de.htwg.memory.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import de.htwg.memory.entities.Highscore;
import de.htwg.memory.entities.IHighscore;
import de.htwg.memory.persistence.IHighscoreDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HighscoreHibernateDAO implements IHighscoreDAO {

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
			Session session = HighscoreUtil.getInstance().getCurrentSession();
			phighscore = (PersistentHighscore) session.get(PersistentHighscore.class, highscoreId);
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
		Transaction tx = null;
		Session session = null;

		try {
			session = HighscoreUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentHighscore phighscore = copyHighscore(highscore);
			
			session.saveOrUpdate(phighscore);

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
		}
	}

	@Override
	public boolean containsHighscoreById(String id) {
		if(getHighscoreById(id) != null) {
			return true;
		}
		return false;
	}

	@Override
	public IHighscore getHighscoreById(String id) {
		Session session = HighscoreUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		
		return copyHighscore((PersistentHighscore) session.get(PersistentHighscore.class, id));
	}

	@Override
	public void deleteHighscoreById(String id) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HighscoreUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentHighscore phighscore = (PersistentHighscore) session.get(PersistentHighscore.class, id);

			session.delete(phighscore);

			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
		    }
		}
	}

	@Override
	public List<IHighscore> getAllHighscores() {
		Session session = HighscoreUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PersistentHighscore.class);
		
		@SuppressWarnings("unchecked")
		List<PersistentHighscore> results = criteria.list();

		List<IHighscore> highscores = new ArrayList<IHighscore>();
		for (PersistentHighscore phighscore : results) {
			IHighscore highscore = copyHighscore(phighscore);
			highscores.add(highscore);
		}
		return highscores;
	}
}
