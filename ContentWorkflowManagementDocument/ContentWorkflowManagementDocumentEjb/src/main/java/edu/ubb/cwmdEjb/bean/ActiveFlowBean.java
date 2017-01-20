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
import edu.ubb.cwmdEjb.dao.ActiveFlowDAO;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
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

}
