package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;

@Remote
public interface TemplateBeanInterface {

	TemplateDTO findById(Long templateId) throws RemoteException;

	List<TemplateDTO> getTemplates() throws RemoteException;

	void insertTemplate(TemplateDTO templateDTO) throws RemoteException;

	void deleteTemplate(TemplateDTO templateDTO) throws RemoteException;

	boolean templateHasDocuments(TemplateDTO templateDTO) throws RemoteException;
}
