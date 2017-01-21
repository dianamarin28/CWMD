package edu.ubb.cwmdEjb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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

import edu.ubb.cwmdEjb.model.Department;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.model.VersionStatus;

@Stateless(name = "DocumentDAO", mappedName = "ejb/DocumentDAO")
public class DocumentDAO {

	private static Logger logger = LoggerFactory.getLogger(DocumentDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	@EJB
	private UserDAO userDao;

	public void insertDocument(Document document) throws DaoException {
		try {
			entityManager.persist(document);
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Document insertion failed", e);
			throw new DaoException("Document insertion failed", e);
		}
	}

	public Document updateDocument(Document document) throws DaoException {
		try {
			document = entityManager.merge(document);
			entityManager.flush();
			return document;
		} catch (PersistenceException e) {
			logger.error("Document update failed ", e);
			throw new DaoException("Document update failed ", e);
		}
	}

	public Document findById(Long documentId) throws DaoException {
		try {
			Document document = entityManager.find(Document.class, documentId);
			if (document == null) {
				throw new DaoException("Cannot find document for the id: " + documentId);
			}
			return document;
		} catch (PersistenceException e) {
			logger.error("Document retrieval by id failed", e);
			throw new DaoException("Document retrieval by id failed", e);
		}

	}

	public List<Document> getAllDocuments() throws DaoException {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Document> cq = cb.createQuery(Document.class);
			Root<Document> root = cq.from(Document.class);
			CriteriaQuery<Document> allEntities = cq.select(root);
			TypedQuery<Document> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Documentss retrieval failed", e);
			throw new DaoException("Documents retrieval failed", e);
		}
	}

	public void deleteDocument(Document document) throws DaoException {
		try {
			document = findById(document.getId());
			entityManager.remove(document);
		} catch (PersistenceException e) {
			logger.error("Document deletion failed", e);
			throw new DaoException("Document deletion failed", e);
		}
	}

	public Document getByNameAndUserId(String documentName, Long userId) throws DaoException {
		try {
			TypedQuery<Document> query = entityManager.createQuery(
					"SELECT d FROM Document d JOIN d.author a WHERE d.name = :documentName AND a.id = :userId",
					Document.class);
			query.setParameter("documentName", documentName);
			query.setParameter("userId", userId);
			List<Document> documents = query.getResultList();
			if (documents.size() == 1) {
				return documents.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			logger.error("Document retrieval by name and user id failed", e);
			throw new DaoException("Document retrieval by name and user id failed", e);
		}
	}

	public List<Document> getByUserId(Long userId) throws DaoException {
		try {
			TypedQuery<Document> query = entityManager
					.createQuery("SELECT d FROM Document d JOIN d.author a WHERE a.id = :userId", Document.class);
			query.setParameter("userId", userId);
			List<Document> documnents = query.getResultList();
			return documnents;
		} catch (PersistenceException e) {
			logger.error("Document retrieval by user id failed", e);
			throw new DaoException("Document retrieval by and user id failed", e);
		}
	}

	public List<Document> getDocumentsForAdministrator(Long userId) throws DaoException {
		Department department = null;
		try {
			department = userDao.findById(userId).getFunction().getDepartment();
		} catch (DaoException e) {
			throw new DaoException(e.getMessage(), e);
		}
		Long departmentId = department.getId();

		List<User> usersFromAdminDepartment = new ArrayList<>();
		try {
			usersFromAdminDepartment = userDao.getUserFromDepartment(departmentId);
		} catch (DaoException e) {
			throw new DaoException(e.getMessage(), e);
		}

		List<Document> documents = new ArrayList<>();
		try {
			for (User user : usersFromAdminDepartment) {
				List<Document> currentUserDocuments = getByUserId(user.getId());
				documents.addAll(currentUserDocuments);
			}
			return documents;
		} catch (DaoException | PersistenceException e) {
			logger.error("Document retrieval by user id for a list of users failed", e);
			throw new DaoException("Document retrieval by and user id for a list of users failed", e);
		}
	}
	
	public List<Document> getDocumentsForTemplate(Long templateId)  throws DaoException {
		try {
			TypedQuery<Document> query = entityManager
					.createQuery("SELECT d FROM Document d JOIN d.template t WHERE t.id = :templateId", Document.class);
			query.setParameter("templateId", templateId);
			List<Document> documnents = query.getResultList();
			return documnents;
		} catch (PersistenceException e) {
			logger.error("Document retrieval by template id failed", e);
			throw new DaoException("Document retrieval by and template id failed", e);
		}
	}
	
	public List<Document> getFinalDocumentsNotInActiveFlows() throws DaoException{
		try{
			TypedQuery<Document> query = entityManager
					
					.createQuery("SELECT d FROM Document d JOIN d.version v WHERE v.document.id = d.id AND v.status = :final and v.activeFlowId IS NULL", Document.class);
			
			query.setParameter("final", VersionStatus.FINAL);
			List<Document> documents = query.getResultList();
			return documents;
		}
		catch(PersistenceException e){
			logger.error("Get final documents not in active flows failed", e);
			throw new DaoException("Get final documents not in active flows failed", e);
		}
	}
	
}
