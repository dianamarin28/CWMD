package edu.ubb.cwmdEjb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Log;

@Stateless(name = "LogDAO", mappedName = "ejb/LogDAO")
public class LogDAO {

	private static Logger logger = LoggerFactory.getLogger(LogDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public void insertLog(Log log) throws DaoException {
		try {
			entityManager.persist(log);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("Log insertion failed", e);
			throw new DaoException("Log insertion failed", e);
		}
	}

}
