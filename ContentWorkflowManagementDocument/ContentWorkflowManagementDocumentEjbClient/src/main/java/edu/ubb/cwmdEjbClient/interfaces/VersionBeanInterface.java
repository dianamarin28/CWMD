package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.VersionDTO;

@Remote
public interface VersionBeanInterface {

	void insertVersion(VersionDTO versionDTO) throws RemoteException;

	VersionDTO findById(Long versionId) throws RemoteException;

	Double getLatestVersionNumberForDraft(Long documentId) throws RemoteException;

	Double getLatestVersionNumberForFinal(Long documentId) throws RemoteException;

	List<VersionDTO> getAllVersionsOfDocument(Long documentId) throws RemoteException;

	void deleteVersion(VersionDTO versionDTO) throws RemoteException;

	List<VersionDTO> getAllVersionsFromActiveFlow(Long activeFlowId) throws RemoteException;

	VersionDTO updateVersion(VersionDTO versionDTO) throws RemoteException;
	
	void addActiveFlowIdToVersions(Long activeFlowId, List<VersionDTO> versions) throws RemoteException;

}
