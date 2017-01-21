package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.ActiveFlowAssembler;
import edu.ubb.cwmdEjb.assemblers.FlowAssembler;
import edu.ubb.cwmdEjb.assemblers.UserAssembler;
import edu.ubb.cwmdEjb.dao.ActiveFlowDAO;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Flow;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;

import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.ActiveFlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class ActiveFlowBean implements Serializable, ActiveFlowBeanInterface {

	private static final long serialVersionUID = -615928543976140525L;

	private static Logger logger = LoggerFactory.getLogger(VersionBean.class);

	@EJB
	private ActiveFlowDAO activeFlowDAO;

	private ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
	private FlowAssembler flowAssembler = new FlowAssembler();
	private UserAssembler userAssembler = new UserAssembler();	
	
	@Override
	public ActiveFlowDTO findById(Long activeFlowId) throws RemoteException {
		ActiveFlowDTO activeFlowDTO = new ActiveFlowDTO();
		try {
			activeFlowDTO = activeFlowAssembler.modelToDto(activeFlowDAO.findById(activeFlowId));
			return activeFlowDTO;
		} catch (DaoException e) {
			logger.error("Find version by id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<ActiveFlowDTO> getActiveByUserId(Long userId) throws RemoteException {
		try {
			List<ActiveFlowDTO> activeFlowDTOs = new ArrayList<>();
			List<ActiveFlow> activeFlows = activeFlowDAO.getActiveByUserId(userId);
			for (ActiveFlow af : activeFlows) {
				activeFlowDTOs.add(activeFlowAssembler.modelToDto(af));
			}
			return activeFlowDTOs;
		} catch (DaoException e) {
			logger.error("Get active flows active by user id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<ActiveFlowDTO> getFinishedByUserId(Long userId) throws RemoteException {
		try {
			List<ActiveFlowDTO> activeFlowDTOs = new ArrayList<>();
			List<ActiveFlow> activeFlows = activeFlowDAO.getFinishedByUserId(userId);
			for (ActiveFlow af : activeFlows) {
				activeFlowDTOs.add(activeFlowAssembler.modelToDto(af));
			}
			return activeFlowDTOs;
		} catch (DaoException e) {
			logger.error("Get active flows finished by user id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<ActiveFlowDTO> getActiveForAdministrator(Long userId) throws RemoteException {
		try {
			List<ActiveFlowDTO> activeFlowDTOs = new ArrayList<>();
			List<ActiveFlow> activeFlows = activeFlowDAO.getActiveForAdministrator(userId);
			for (ActiveFlow activeFlow : activeFlows) {
				activeFlowDTOs.add(activeFlowAssembler.modelToDto(activeFlow));
			}
			return activeFlowDTOs;
		} catch (DaoException e) {
			logger.error("Get active flows actvive for administrator error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<ActiveFlowDTO> getFinishedForAdministrator(Long userId) throws RemoteException {
		try {
			List<ActiveFlowDTO> activeFlowDTOs = new ArrayList<>();
			List<ActiveFlow> activeFlows = activeFlowDAO.getFinishedForAdministrator(userId);
			for (ActiveFlow activeFlow : activeFlows) {
				activeFlowDTOs.add(activeFlowAssembler.modelToDto(activeFlow));
			}
			return activeFlowDTOs;
		} catch (DaoException e) {
			logger.error("Get active flows finished for administrator error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public ActiveFlowDTO updateActiveFlow(ActiveFlowDTO activeFlowDTO) throws RemoteException {
		ActiveFlow activeFlow = activeFlowAssembler.dtoToModel(activeFlowDTO);
		try {
			ActiveFlowDTO updatedDTO = activeFlowAssembler.modelToDto(activeFlowDAO.updateActiveFlow(activeFlow));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("Update active flow error: " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
	@Override
	public Long createActiveFlow(UserDTO userDTO, FlowDTO flowDTO, String flowName) throws RemoteException{
		try{
			User user = userAssembler.dtoToModel(userDTO);
			Flow flow = flowAssembler.dtoToModel(flowDTO);
			activeFlowDAO.createActiveFlow(user, flow, flowName);
		}
		catch(DaoException e){
			logger.error("Create active flow error: " + e);
			throw new RemoteException(e.getMessage(), e);
		}
		return new Long(1);
	}
	
//	@Override
//	public void rejectActiveFlow(ActiveFlowDTO activeFlow) throws RemoteException{
//		try{
//			
//		}
//	}

}
