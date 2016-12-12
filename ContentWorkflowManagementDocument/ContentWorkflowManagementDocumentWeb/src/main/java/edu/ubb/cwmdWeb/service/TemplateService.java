package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdEjbClient.interfaces.TemplateBeanInterface;

@ManagedBean(name = "templateService", eager = true)
@ApplicationScoped
public class TemplateService {

	@EJB
	private TemplateBeanInterface templateBeanInterface;

	public TemplateDTO getTemplate(Long id) {
		return templateBeanInterface.findById(id);
	}

}
