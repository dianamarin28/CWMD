package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.LogDTO;

@Remote
public interface LogBeanInterface {

	void insertLog(LogDTO logDTO) throws RemoteException;

	List<LogDTO> getLogsByType(String type) throws RemoteException;

}
