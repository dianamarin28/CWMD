package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.TemplateAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.TemplateDAO;
import edu.ubb.cwmdEjb.model.Template;
import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TemplateBeanInterface;

@Stateless
@LocalBean
public class TemplateBean implements Serializable, TemplateBeanInterface {

	private static final long serialVersionUID = 2683636042080897142L;

	private static Logger logger = LoggerFactory.getLogger(TemplateBean.class);

	@EJB
	private TemplateDAO templateDao;

	private TemplateAssembler templateAssembler = new TemplateAssembler();

	@Override
	public TemplateDTO findById(Long templateId) throws RemoteException {
		TemplateDTO template = new TemplateDTO();
		try {
			template = templateAssembler.modelToDtoSimple(templateDao.findById(templateId));
			return template;
		} catch (DaoException e) {
			logger.error("Error while finding the template by id" + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<TemplateDTO> getTemplates() throws RemoteException {
		List<TemplateDTO> templatesDTOs = new ArrayList<>();
		try {
			List<Template> list = templateDao.getTemplates();
			for (Template template : list) {
				TemplateDTO templateDTO = templateAssembler.modelToDtoSimple(template);
				templatesDTOs.add(templateDTO);
			}
			return templatesDTOs;
		} catch (DaoException e) {
			logger.error("Error while retrieving the templates " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
