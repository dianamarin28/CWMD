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

import edu.ubb.cwmdEjb.model.Flow;

@Stateless(name = "FlowDAO", mappedName = "ejb/FlowDAO")
public class FlowDAO {

	private static Logger logger = LoggerFactory.getLogger(FlowDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public void insertFlow(Flow flow) throws DaoException {
		try {
			entityManager.persist(flow);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("Flow insertion failed", e);
			throw new DaoException("Flow insertion failed", e);
		}
	}

	public Flow findById(Long flowId) throws DaoException {
		try {
			Flow flow = entityManager.find(Flow.class, flowId);
			if (flow == null) {
				throw new DaoException("Can't find flow for ID " + flowId);
			}
			return flow;
		} catch (PersistenceException e) {
			logger.error("Flwo retrieval by id failed", e);
			throw new DaoException("Flow retrieval by id failed", e);
		}

	}

	public void deleteFlow(Flow flow) throws DaoException {
		try {
			flow = findById(flow.getId());
			entityManager.remove(flow);

		} catch (PersistenceException e) {
			logger.error("Flow deletion failed ", e);
			throw new DaoException("Flow deletion failed ", e);
		}
	}

	public List<Flow> getFlows() throws DaoException {
		
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Flow> cq = cb.createQuery(Flow.class);
			Root<Flow> root = cq.from(Flow.class);
			CriteriaQuery<Flow> allEntities = cq.select(root);
			TypedQuery<Flow> tq = entityManager.createQuery(allEntities);
			System.out.println("size of flows: "+tq.getResultList().size());
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Flows retrieval failed", e);
			throw new DaoException("Flows retrieval failed", e);
		}
	}
}
