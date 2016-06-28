package de.htwg.memory.main;

import com.google.inject.AbstractModule;
import de.htwg.memory.logic.Controller;
import de.htwg.memory.logic.IController;
import de.htwg.memory.persistence.IHighscoreDAO;


public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IController.class).to(Controller.class).asEagerSingleton();
//		bind(IHighscoreDAO.class).to(de.htwg.memory.persistence.couchdb.HighscoreCouchdbDAO.class);
        bind(IHighscoreDAO.class).to(de.htwg.memory.persistence.hibernate.HighscoreHibernateDAO.class);
    }
}
