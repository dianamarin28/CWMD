package edu.ubb.cwmdEjb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.model.Version;

@Stateless(name = "VersionDAO", mappedName = "ejb/VersionDAO")
public class VersionDAO {

	private static Logger logger = LoggerFactory.getLogger(VersionDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

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

}
