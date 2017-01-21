package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;

@Remote
public interface FlowBeanInterface {

	void insertFlow(FlowDTO flowDTO) throws RemoteException;

	void deleteFlow(FlowDTO flowDTO) throws RemoteException;

	List<FlowDTO> getFlows() throws RemoteException;
	
	boolean flowHasActiveFlows(FlowDTO flowDTO) throws RemoteException;
	
	FlowDTO findById(Long flowId) throws RemoteException;

}
