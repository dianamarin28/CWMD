package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.FlowAssembler;
import edu.ubb.cwmdEjb.dao.ActiveFlowDAO;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.FlowDAO;
import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Flow;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class FlowBean implements Serializable, FlowBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(FlowBean.class);
	private static final long serialVersionUID = 3315250089472767790L;

	@EJB
	private FlowDAO flowDAO;

	@EJB
	private ActiveFlowDAO activeFlowDAO;

	private FlowAssembler flowAssembler = new FlowAssembler();

	@Override
	public void insertFlow(FlowDTO flowDTO) throws RemoteException {
		Flow flow = flowAssembler.dtoToModel(flowDTO);
		try {
			flowDAO.insertFlow(flow);

		} catch (DaoException e) {
			logger.error("insert flow error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public boolean flowHasActiveFlows(FlowDTO flowDTO) throws RemoteException {
		try {
			List<ActiveFlow> activeFlows = activeFlowDAO.getActiveFlowForFlow(flowDTO.getFlowId());
			if (activeFlows.isEmpty()) {
				return false;
			}
			return true;
		} catch (DaoException e) {
			logger.error("Get active flows of a flow error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteFlow(FlowDTO flowDTO) throws RemoteException {
		Flow flow = flowAssembler.dtoToModel(flowDTO);
		// if(flow.getActiveFlows() != null) {
		// logger.error("Cannot delete flow that has active flows");
		// throw new RemoteException("Cannot delete flow that has active
		// flows");
		// }
		try {
			flowDAO.deleteFlow(flow);
		} catch (DaoException e) {
			logger.error("delete flow error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<FlowDTO> getFlows() throws RemoteException {
		List<FlowDTO> flowDTOlist = new ArrayList<>();
		try {
			List<Flow> flowsList = flowDAO.getFlows();
			for (Flow flow : flowsList) {
				FlowDTO flowDTO = flowAssembler.modelToDto(flow);
				flowDTOlist.add(flowDTO);
			}
			return flowDTOlist;
		} catch (DaoException e) {
			logger.error("get all flows error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
