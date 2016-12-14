package edu.ubb.cwmdEjbClient.interfaces;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.VersionDTO;

@Remote
public interface VersionBeanInterface {

	void insertVersion(VersionDTO versionDTO) throws RemoteException;

}
