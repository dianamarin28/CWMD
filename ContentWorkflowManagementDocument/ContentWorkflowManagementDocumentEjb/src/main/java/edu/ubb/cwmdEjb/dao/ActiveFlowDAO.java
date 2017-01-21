package edu.ubb.cwmdEjb.dao;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.ActiveFlowStatus;
import edu.ubb.cwmdEjb.model.Department;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.model.Flow;

@Stateless(name = "ActiveFlowDAO", mappedName = "ejb/ActiveFlowDAO")
public class ActiveFlowDAO {

	private static Logger logger = LoggerFactory.getLogger(ActiveFlowDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	@EJB
	private UserDAO userDao;

	public Long createActiveFlow(User user, Flow flow, String flowName) throws DaoException{
		System.out.println("User id, flowId: " + user.getId() + flow.getId());
		try{
			ActiveFlow newFlow = new ActiveFlow();
			flow.setName(flowName);
			newFlow.setUser(user);
			newFlow.setFlow(flow);
			newFlow.setStatus(ActiveFlowStatus.ACTIVE);
			newFlow.setStep("P2");
			
			entityManager.persist(newFlow);
			entityManager.flush();
		}
		catch(PersistenceException e){
			logger.error("Create new active flow error", e);
			throw new DaoException("Create new active flow error", e);
		}
		return new Long(1);
	}
	
	public ActiveFlow findById(Long activeFlowId) throws DaoException {
		try {
			ActiveFlow activeFlow = entityManager.find(ActiveFlow.class, activeFlowId);
			if (activeFlow == null) {
				throw new DaoException("Cannot find active flow for the id: " + activeFlowId);
			}
			return activeFlow;
		} catch (PersistenceException e) {
			logger.error("Active flow retrieval by id failed", e);
			throw new DaoException("Active flow retrieval by id failed", e);
		}
	}

	public ActiveFlow updateActiveFlow(ActiveFlow activeFlow) throws DaoException {
		try {
			activeFlow = entityManager.merge(activeFlow);
			entityManager.flush();
			return activeFlow;
		} catch (PersistenceException e) {
			logger.error("Active flow update failed ", e);
			throw new DaoException("Active flow update failed", e);
		}
	}

	public List<ActiveFlow> getActiveByUserId(Long userId) throws DaoException {
		try {
			TypedQuery<ActiveFlow> query = entityManager.createQuery(
					"SELECT a FROM ActiveFlow a JOIN a.user u WHERE u.id = :userId AND a.status = :status",
					ActiveFlow.class);
			query.setParameter("userId", userId);
			query.setParameter("status", ActiveFlowStatus.ACTIVE);
			List<ActiveFlow> activeFlows = query.getResultList();
			return activeFlows;
		} catch (PersistenceException e) {
			logger.error("Active flow active retrieval by user id failed", e);
			throw new DaoException("Active flow active retrieval by user id failed", e);
		}
	}

	public List<ActiveFlow> getActiveForAdministrator(Long userId) throws DaoException {
		try {
			Department department = userDao.findById(userId).getFunction().getDepartment();
			Long departmentId = department.getId();

			List<User> usersFromAdminDepartment = userDao.getUserFromDepartment(departmentId);

			List<ActiveFlow> activeFlows = new ArrayList<>();
			for (User user : usersFromAdminDepartment) {
				List<ActiveFlow> currentUserActiveFlows = getActiveByUserId(user.getId());
				activeFlows.addAll(currentUserActiveFlows);
			}
			System.out.println("ADMIN dao" + activeFlows);
			return activeFlows;
		} catch (PersistenceException e) {
			logger.error("Active flow active retrieval by user id for a list of users failed", e);
			throw new DaoException("Active flow active retrieval by user id for a list of users failed", e);
		} catch (DaoException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	public List<ActiveFlow> getFinishedByUserId(Long userId) throws DaoException {
		try {
			TypedQuery<ActiveFlow> query = entityManager.createQuery(
					"SELECT a FROM ActiveFlow a JOIN a.user u WHERE u.id = :userId AND a.status = :status",
					ActiveFlow.class);
			query.setParameter("userId", userId);
			query.setParameter("status", ActiveFlowStatus.FINISHED);
			List<ActiveFlow> activeFlows = query.getResultList();
			return activeFlows;
		} catch (PersistenceException e) {
			logger.error("Active flow finished retrieval by user id failed", e);
			throw new DaoException("Active flow finished retrieval by user id failed", e);
		}
	}

	public List<ActiveFlow> getFinishedForAdministrator(Long userId) throws DaoException {
		try {
			Department department = userDao.findById(userId).getFunction().getDepartment();
			Long departmentId = department.getId();

			List<User> usersFromAdminDepartment = userDao.getUserFromDepartment(departmentId);

			List<ActiveFlow> activeFlows = new ArrayList<>();
			for (User user : usersFromAdminDepartment) {
				List<ActiveFlow> currentUserActiveFlows = getFinishedByUserId(user.getId());
				activeFlows.addAll(currentUserActiveFlows);
			}
			return activeFlows;
		} catch (PersistenceException e) {
			logger.error("Finished flow active retrieval by user id for a list of users failed", e);
			throw new DaoException("Finished flow active retrieval by user id for a list of users failed", e);
		} catch (DaoException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	public List<ActiveFlow> getActiveFlowForFlow(Long flowId) throws DaoException {
		try {
			TypedQuery<ActiveFlow> query = entityManager
					.createQuery("SELECT a FROM ActiveFlow a JOIN a.flow f WHERE f.id = :flowId", ActiveFlow.class);
			query.setParameter("flowId", flowId);
			List<ActiveFlow> activeFlows = query.getResultList();
			return activeFlows;
		} catch (PersistenceException e) {
			logger.error("Active flows retrieval by flow id failed", e);
			throw new DaoException("Active flows retrieval by and flow id failed", e);
		}

	}

}
