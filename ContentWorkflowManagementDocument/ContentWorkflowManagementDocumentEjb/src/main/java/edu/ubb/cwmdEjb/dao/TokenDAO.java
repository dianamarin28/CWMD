package edu.ubb.cwmdEjb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Token;
import edu.ubb.cwmdEjb.model.User;

@Stateless(name = "TokenDAO", mappedName = "ejb/TokenDAO")
public class TokenDAO {

	private static Logger logger = LoggerFactory.getLogger(TokenDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public Token insertToken(Token token) throws DaoException {
		try {
			entityManager.persist(token);
			entityManager.flush();
			return token;
		} catch (PersistenceException e) {
			logger.error("Token insertion failed", e);
			throw new DaoException("Token insertion failed", e);
		}
	}

	public Token getByValue(String value) throws DaoException {
		try {
			TypedQuery<Token> tokens = entityManager.createQuery("SELECT t FROM Token t WHERE t.value= :value",
					Token.class);
			tokens.setParameter("value", value);
			Token token = tokens.getSingleResult();
			return token;
		} catch (PersistenceException ex) {
			logger.error("Token selection by value failed", ex);
			throw new DaoException("Token selection by value failed", ex);
		}
	}

	public void deleteAllHavingUser(User user) throws DaoException {
		try {
			TypedQuery<Token> query = entityManager.createQuery("DELETE FROM Token t WHERE t.user.id = :userId",
					Token.class);
			query.setParameter("userId", user.getId());
			query.executeUpdate();
		} catch (PersistenceException ex) {
			logger.error("Deletion of the tokens of a user failed", ex);
			throw new DaoException("Deletion of the tokens of a user failed", ex);
		}

	}

}
