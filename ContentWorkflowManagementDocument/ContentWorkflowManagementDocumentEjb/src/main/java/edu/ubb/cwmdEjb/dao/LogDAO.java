package edu.ubb.cwmdEjb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Log;
import edu.ubb.cwmdEjb.model.LogActionType;

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

	public List<Log> getLogsByType(LogActionType logActionType) throws DaoException {
		try {
			TypedQuery<Log> query = entityManager
					.createQuery("SELECT l FROM Log l WHERE l.logActionType = :logActionType", Log.class);
			query.setParameter("logActionType", logActionType);
			return query.getResultList();
		} catch (PersistenceException e) {
			logger.error("Logs retrieval by type failed", e);
			throw new DaoException("Logs retrieval by type failed", e);
		}
	}

}
