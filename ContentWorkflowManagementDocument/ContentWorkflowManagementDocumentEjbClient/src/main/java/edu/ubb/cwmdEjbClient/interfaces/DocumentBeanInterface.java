package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;

@Remote
public interface DocumentBeanInterface {

	void insertDocument(DocumentDTO documentDTO) throws RemoteException;

	DocumentDTO getByNameAndUserId(String documentName, Long userId) throws RemoteException;

	List<DocumentDTO> getDocumentsForUserID(Long userId) throws RemoteException;

	List<DocumentDTO> getDocumentsForAdministrator(Long userId) throws RemoteException;

	DocumentDTO findById(Long documentId) throws RemoteException;

	DocumentDTO updateDocument(DocumentDTO documentDTO) throws RemoteException;

	void deleteDocument(DocumentDTO documentDTO) throws RemoteException;
	
	List<DocumentDTO> getFinalDocumentsNotInActiveFlows() throws RemoteException;
}
