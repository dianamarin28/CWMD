package edu.ubb.cwmdEjb.dao;

import java.util.ArrayList;
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

import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjb.model.VersionStatus;

@Stateless(name = "VersionDAO", mappedName = "ejb/VersionDAO")
public class VersionDAO {

	private static Logger logger = LoggerFactory.getLogger(VersionDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public List<Version> getVersions() throws DaoException {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Version> cq = cb.createQuery(Version.class);
			Root<Version> root = cq.from(Version.class);
			CriteriaQuery<Version> allEntities = cq.select(root);
			TypedQuery<Version> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();
		} catch (PersistenceException e) {
			logger.error("Versions retrieval failed", e);
			throw new DaoException("Versions retrieval failed", e);
		}
	}

	public void insertVersion(Version version) throws DaoException {
		try {
			entityManager.persist(version);
			entityManager.flush();

		} catch (PersistenceException e) {
			logger.error("Version insertion failed", e);
			throw new DaoException("Version insertion failed", e);
		}
	}

	public Version findById(Long versionId) throws DaoException {
		try {
			Version version = entityManager.find(Version.class, versionId);
			if (version == null) {
				throw new DaoException("Cannot find version for id: " + versionId);
			}
			return version;
		} catch (PersistenceException e) {
			logger.error("Version retrieval by id failed", e);
			throw new DaoException("Version retrieval by id failed", e);
		}
	}

	public void deleteVersion(Version version) throws DaoException {
		try {
			version = findById(version.getId());
			entityManager.remove(version);
		} catch (PersistenceException e) {
			logger.error("Version deletion failed", e);
			throw new DaoException("Version deletion failed", e);
		}
	}

	public Double getLatestVersionNumberDraft(Long documentId) {
		try {
			TypedQuery<Version> query = entityManager.createQuery(
					"SELECT v FROM Version v JOIN v.document d WHERE d.id = :documentId AND v.status = :draft",
					Version.class);
			query.setParameter("documentId", documentId);
			query.setParameter("draft", VersionStatus.DRAFT);
			List<Version> versions = query.getResultList();
			Double latestVersionNumber = 0.0;
			for (Version version : versions) {
				if (version.getNumber() > latestVersionNumber) {
					latestVersionNumber = version.getNumber();
				}
			}
			return latestVersionNumber;
		} catch (PersistenceException e) {
			logger.error("Version number retrieval by document id failed", e);
			throw new DaoException("Version number retrieval by document id failed", e);
		}
	}

	public Double getLatestVersionNumberFinal(Long documentId) {
		try {
			TypedQuery<Version> query = entityManager.createQuery(
					"SELECT v FROM Version v JOIN v.document d WHERE d.id = :documentId AND v.status = :final",
					Version.class);
			query.setParameter("documentId", documentId);
			query.setParameter("final", VersionStatus.FINAL);
			List<Version> versions = query.getResultList();
			Double latestVersionNumber = 0.0;
			for (Version version : versions) {
				if (version.getNumber() > latestVersionNumber) {
					latestVersionNumber = version.getNumber();
				}
			}
			return latestVersionNumber;
		} catch (PersistenceException e) {
			logger.error("Version number retrieval by document id failed", e);
			throw new DaoException("Version number retrieval by document id failed", e);
		}
	}

	public List<Version> getAllVersionsOfDocument(Long documentId) throws DaoException {
		try {
			// TypedQuery<Version> query = entityManager
			// .createQuery("SELECT v FROM Version v JOIN v.document d WHERE
			// d.id = :documentId", Version.class);
			// query.setParameter("documentId", documentId);
			// List<Version> versions = query.getResultList();
			List<Version> versions = getVersions();
			List<Version> validVersions = new ArrayList<>();
			for (Version v : versions) {
				if (v.getDocument().getId().equals(documentId)) {
					validVersions.add(v);
				}
			}

			return validVersions;
		} catch (PersistenceException e) {
			logger.error("Versions retrieval by document id failed", e);
			throw new DaoException("Version retrieval by document id failed", e);
		}
	}

	public List<Version> getAllVersionsFromActiveFlow(Long activeFlowId) throws DaoException {
		try {
			TypedQuery<Version> query = entityManager.createQuery(
					"SELECT v FROM Version v JOIN v.activeFlow a WHERE a.id = :activeFlowId", Version.class);
			query.setParameter("activeFlowId", activeFlowId);
			List<Version> versions = query.getResultList();
			return versions;
		} catch (PersistenceException e) {
			logger.error("Versions retrieval by active flow id failed", e);
			throw new DaoException("Versions retrieval by active flow id failed", e);
		}
	}

	public Version updateVersion(Version version) throws DaoException {
		try {
			version = entityManager.merge(version);
			entityManager.flush();
			return version;
		} catch (PersistenceException e) {
			logger.error("Version update failed", e);
			throw new DaoException("Version update failed", e);
		}
	}

}
