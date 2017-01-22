package edu.ubb.cwmdEjb.dao;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.exceptions.StaticWeaveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.ActiveFlowStatus;
import edu.ubb.cwmdEjb.model.Department;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjb.model.VersionStatus;
import edu.ubb.cwmdEjb.model.Flow;

@Stateless(name = "ActiveFlowDAO", mappedName = "ejb/ActiveFlowDAO")
public class ActiveFlowDAO {

	private static Logger logger = LoggerFactory.getLogger(ActiveFlowDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	@EJB
	private UserDAO userDao;

	@EJB
	private FunctionDAO functionDao;
	
	@EJB
	private VersionDAO versionDao;

	public Long createActiveFlow(User user, Flow flow, String flowName) throws DaoException {
		System.out.println("User id, flowId: " + user.getId() + flow.getId());
		try {
			ActiveFlow newFlow = new ActiveFlow();
			flow.setName(flowName);
			newFlow.setUser(user);
			newFlow.setFlow(flow);
			newFlow.setStatus(ActiveFlowStatus.ACTIVE);
			newFlow.setStep("P2");

			entityManager.persist(newFlow);
			entityManager.flush();
		} catch (PersistenceException e) {
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

	public List<ActiveFlow> getActiveByStep(Long userId) throws DaoException {
		try {
			TypedQuery<ActiveFlow> query = entityManager.createQuery(
					"SELECT a FROM ActiveFlow a WHERE a.status = :status",
					ActiveFlow.class);
			query.setParameter("status", ActiveFlowStatus.ACTIVE);
			List<ActiveFlow> activeFlows = query.getResultList();
			
			User user = userDao.findById(userId);

			System.out.println("in method step somth funtyp");
			System.out.println(user.getFunction().getType());
			logger.error("in method step somth funtyp");
			logger.error(user.getFunction().getType());
			
			List<ActiveFlow> theActiveFlows = new ArrayList<>();
			for (ActiveFlow activeFlow : activeFlows) {
				TypedQuery<Flow> query1 = entityManager.createQuery(
					//	select t.* from (select af.*, f.configuredflowid as newid from activeflow af join flow f on af.flowId=f.id) t join configuredflow cf on t.newId=cf.id where  cf.p2='SECR'
//select * from flow f join configuredFlow cf on f.configuredflowid = cf.id where f.id = 2 and cf.p2 ='SECR'

						"select f FROM Flow f join ConfiguredFlow cf on f.configuredFlow.id = cf.id WHERE f.id = "+activeFlow.getFlow().getId()+" and cf." + activeFlow.getStep() + " = :functionType",
						Flow.class);
				query1.setParameter("functionType", user.getFunction().getType());
				if(query1.getResultList().size()!=0)
				{
					theActiveFlows.add(activeFlow);

				}
			}
			
			System.out.println(theActiveFlows.size());
			return theActiveFlows;
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

	public void insertActiveFlow(ActiveFlow activeFlow) throws DaoException {
		try {
			entityManager.persist(activeFlow);
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Active flow insertion failed", e);
			throw new DaoException("Active flow insertion failed", e);
		}
	}

	public void rejectActiveFlow(ActiveFlow activeFlow) throws DaoException
	{
		try{
			String step = activeFlow.getStep();
			if(step.equals("p2"))
			{
				activeFlow.setStatus(ActiveFlowStatus.REJECTED);
			}
			
			int i = Character.getNumericValue(step.charAt(1));
			i--;
			String newStep = "p" + i;
			activeFlow.setStep(newStep);
		

			this.updateActiveFlow(activeFlow);
		}
		catch(PersistenceException e){
			logger.error("Active flow rejection failed", e);
			throw new DaoException("Active flow rejection failed", e);
		}
	}
	
	public void approveActiveFlow(ActiveFlow activeFlow) throws DaoException
	{
		try{
			int noOfParticipants = activeFlow.getFlow().getNoOfParticipants();
			String step =  activeFlow.getStep();
			int nr = Character.getNumericValue(step.charAt(1));
			int nextNr = nr + 1;
			System.out.println("nr is: " + nextNr);
			if(nr == noOfParticipants){
				activeFlow.setStatus(ActiveFlowStatus.FINISHED);
			}
			String nextStep = "p" + nextNr;
			activeFlow.setStep(nextStep);
			
			this.updateActiveFlow(activeFlow);
			
			if(nr == noOfParticipants){
				for (Version version : activeFlow.getVersions()) {
					version.setStatus(VersionStatus.BLOCKED);
					versionDao.updateVersion(version);
				}
			}
		}
		catch(PersistenceException e){
			throw new DaoException("Error", e);
		}
	}
}
