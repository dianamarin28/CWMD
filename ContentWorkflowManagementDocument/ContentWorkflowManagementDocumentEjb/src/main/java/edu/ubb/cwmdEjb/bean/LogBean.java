package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.LogAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.LogDAO;
import edu.ubb.cwmdEjb.model.Log;
import edu.ubb.cwmdEjb.model.LogActionType;
import edu.ubb.cwmdEjbClient.dtos.LogDTO;
import edu.ubb.cwmdEjbClient.interfaces.LogBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class LogBean implements Serializable, LogBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(LogBean.class);
	private static final long serialVersionUID = 7891477263249177960L;

	@EJB
	private LogDAO logDAO;

	private LogAssembler logAssembler = new LogAssembler();

	@Override
	public void insertLog(LogDTO logDTO) throws RemoteException {
		Log log = logAssembler.dtoToModel(logDTO);
		try {
			logDAO.insertLog(log);
		} catch (DaoException e) {
			logger.error("Insert log error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<LogDTO> getLogsByType(String type)throws RemoteException {
		List<LogDTO> logsDTOlist = new ArrayList<>();
		try {
			List<Log> logsList = logDAO.getLogsByType(LogActionType.valueOf(type));
			for (Log log : logsList) {
				LogDTO logDto = logAssembler.modelToDto(log);
				logsDTOlist.add(logDto);
			}
			return logsDTOlist;
		} catch (DaoException e) {
			logger.error("getLogsByType error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
