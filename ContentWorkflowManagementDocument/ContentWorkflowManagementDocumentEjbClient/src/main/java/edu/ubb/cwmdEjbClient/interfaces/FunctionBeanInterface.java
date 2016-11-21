package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;

@Remote
public interface FunctionBeanInterface {

	FunctionDTO findById(Long functionId) throws RemoteException;
	
	List<FunctionDTO> getFunctions() throws RemoteException;
}
