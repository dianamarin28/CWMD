package edu.ubb.cwmdEjbClient.interfaces;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.LogDTO;

@Remote
public interface LogBeanInterface {

	void insertLog(LogDTO logDTO) throws RemoteException;

}
