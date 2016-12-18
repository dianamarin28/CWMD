package edu.ubb.cwmdEjbClient.interfaces;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;

@Remote
public interface DocumentBeanInterface {

	void insertDocument(DocumentDTO documentDTO) throws RemoteException;

	DocumentDTO getByNameAndUserId(String documentName, Long userId) throws RemoteException;

}
