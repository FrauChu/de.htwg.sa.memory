package de.htwg.memory.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class HighscoreUtil {
	private static final SessionFactory SESSIONFACTORY;

	static {
		 final AnnotationConfiguration cfg = new
			      AnnotationConfiguration();
			      cfg.configure("/hibernate.cfg.xml");
			      SESSIONFACTORY = cfg.buildSessionFactory();
	}
	
	private HighscoreUtil() {
	}
	
	public static SessionFactory getInstance() {
		return SESSIONFACTORY;
	}

}
