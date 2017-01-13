package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;

@Remote
public interface TemplateBeanInterface {

	TemplateDTO findById(Long templateId) throws RemoteException;

	List<TemplateDTO> getTemplates() throws RemoteException;

}
