package edu.ubb.cwmdEjb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Document;

@Stateless(name = "DocumentDAO", mappedName = "ejb/DocumentDAO")
public class DocumentDAO {

	private static Logger logger = LoggerFactory.getLogger(DocumentDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public void insertDocument(Document document) throws DaoException {
		try {
			entityManager.persist(document);
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Document insertion failed", e);
			throw new DaoException("Document insertion failed", e);
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

}
