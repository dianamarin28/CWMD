package edu.ubb.cwmdEjb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Function;
import edu.ubb.cwmdEjb.model.User;

@Stateless(name = "FunctionDAO", mappedName = "ejb/FunctionDAO")
public class FunctionDAO {
	
	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;
	
	public Function findById(Long functionId) throws DaoException {
		try {
			Function function = entityManager.find(Function.class, functionId);
			if (function == null) {
				throw new DaoException("Can't find function for ID " + functionId);
			}
			return function;
		} catch (PersistenceException e) {
			logger.error("Function retrieval by id failed", e);
			throw new DaoException("Function retrieval by id failed", e);
		}

	}
	
	public List<Function> getFunctions() throws DaoException {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Function> cq = cb.createQuery(Function.class);
			Root<Function> root = cq.from(Function.class);
			CriteriaQuery<Function> allEntities = cq.select(root);
			TypedQuery<Function> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Functions retrieval failed", e);
			throw new DaoException("Functions retrieval failed", e);
		}
	}

}
