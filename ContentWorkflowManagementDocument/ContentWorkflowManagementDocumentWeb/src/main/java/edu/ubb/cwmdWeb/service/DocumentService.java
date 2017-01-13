package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;

@ManagedBean(name = "documentService", eager = true)
@ApplicationScoped
public class DocumentService {

	@EJB
	private DocumentBeanInterface documentBeanInterface;
	
	public DocumentDTO getFunction(Long id) {
		return documentBeanInterface.findById(id);
	}
	
}
