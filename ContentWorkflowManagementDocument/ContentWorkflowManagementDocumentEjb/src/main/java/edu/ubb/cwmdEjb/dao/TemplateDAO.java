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

import edu.ubb.cwmdEjb.model.Template;

@Stateless(name = "TemplateDAO", mappedName = "ejb/TemplateDAO")
public class TemplateDAO {

	private static Logger logger = LoggerFactory.getLogger(TemplateDAO.class);

	@PersistenceContext(unitName = "cwmd")
	private EntityManager entityManager;

	public Template findById(Long templateId) throws DaoException {
		try {
			Template template = entityManager.find(Template.class, templateId);
			if (template == null) {
				throw new DaoException("Can't find template for ID " + templateId);
			}
			return template;
		} catch (PersistenceException e) {
			logger.error("Template retrieval by id failed", e);
			throw new DaoException("Template retrieval by id failed", e);
		}

	}

	public List<Template> getTemplates() throws DaoException {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Template> cq = cb.createQuery(Template.class);
			Root<Template> root = cq.from(Template.class);
			CriteriaQuery<Template> allEntities = cq.select(root);
			TypedQuery<Template> tq = entityManager.createQuery(allEntities);
			return tq.getResultList();

		} catch (PersistenceException e) {
			logger.error("Templates retrieval failed", e);
			throw new DaoException("Templates retrieval failed", e);
		}
	}

	public void insertTemplate(Template template) throws DaoException {
		try {
			entityManager.persist(template);
			entityManager.flush();
		} catch (PersistenceException e) {
			logger.error("Template insertion failed", e);
			throw new DaoException("Template insertion failed", e);
		}
	}

	public void deleteTemplate(Template template) throws DaoException {
		try {
			template = findById(template.getId());
			entityManager.remove(template);
		} catch (PersistenceException e) {
			logger.error("Template deletion failed", e);
			throw new DaoException("Template deletion failed", e);
		}
	}

}
