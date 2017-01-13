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

import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.util.PasswordEncrypter;

@Stateless(name = "UserDAO", mappedName = "ejb/UserDAO")
public class UserDAO {

	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public void insertUser(User user) throws DaoException {
		try {
			entityManager.persist(user);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("User insertion failed", e);
			throw new DaoException("User insertion failed", e);
		}
	}

	public User findById(Long userId) throws DaoException {
		try {
			User user = entityManager.find(User.class, userId);
			if (user == null) {
				throw new DaoException("Can't find User for ID " + userId);
			}
			return user;
		} catch (PersistenceException e) {
			logger.error("Users retrieval by id failed", e);
			throw new DaoException("Users retrieval by id failed", e);
		}

	}

	public User updateUser(User user) throws DaoException {
		try {

			user = entityManager.merge(user);
			entityManager.flush();
			return user;
		} catch (PersistenceException e) {
			logger.error("User update failed ", e);
			throw new DaoException("User update failed ", e);
		}
	}

	public void deleteUser(User user) throws DaoException {
		try {
			user = findById(user.getId());
			entityManager.remove(user);

		} catch (PersistenceException e) {
			logger.error("User deletion failed ", e);
			throw new DaoException("User deletion failed ", e);
		}
	}

	public List<User> getUsers() throws DaoException {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			CriteriaQuery<User> allEntities = cq.select(root);
			TypedQuery<User> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Users retrieval failed", e);
			throw new DaoException("Users retrieval failed", e);
		}
	}

	public List<User> getUsersByRoles(String userRole) throws DaoException {
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userRole = :userRole",
					User.class);
			query.setParameter("userRole", userRole);
			return query.getResultList();

		} catch (PersistenceException e) {
			logger.error("Users retrieval by type failed", e);
			throw new DaoException("Users retrieval by type failed", e);
		}
	}

	public String validateUserNameAndPassword(String userName, String password) throws DaoException {
		try {

			//password = PasswordEncrypter.getGeneratedHashedPassword(password, "");

			TypedQuery<User> query = entityManager.createQuery(
					"SELECT u FROM User u WHERE u.userName = :userName AND u.password = :password", User.class);

			query.setParameter("userName", userName);
			query.setParameter("password", password);

			List<User> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0).getUserRole().toString();
			} else {
				return "";
			}
		} catch (PersistenceException e) {
			logger.error("Validation of username and password failed", e);
			throw new DaoException("Validation of username and password failed", e);
		}

	}

	public User getByUserName(String userName) throws DaoException {
		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :userName",
					User.class);
			query.setParameter("userName", userName);
			List<User> userList = query.getResultList();
			if (userList.size() == 1) {
				return userList.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("User retrieval by Name failed", e);
			throw new DaoException("User retrieval by Name failed", e);
		}
	}
	
	
	public List<User> getUserFromDepartment(Long departmentid)
			throws DaoException {
		try {
			TypedQuery<User> userFromDepartment = entityManager.createQuery(
					"SELECT u FROM User u JOIN u.function f WHERE f.department.id = :id", User.class);
			userFromDepartment.setParameter("id", departmentid);
			List<User> queryResult = userFromDepartment.getResultList();
			
			return queryResult;
		} catch (PersistenceException e) {
			logger.error("Users that have a given department selection failed.", e);
			throw new DaoException("Users that have a given department selection failed.");
		}
	}

}
