package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.FunctionAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.FunctionDAO;
import edu.ubb.cwmdEjb.model.Function;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class FunctionBean implements Serializable,FunctionBeanInterface {
	
	private static final long serialVersionUID = -5911147179236801226L;

	private static Logger logger = LoggerFactory.getLogger(FunctionBean.class);
	
	
	@EJB
	private FunctionDAO functionDao;
	
	private FunctionAssembler functionAssembler = new FunctionAssembler();
	@Override
	public FunctionDTO findById(Long functionId) throws RemoteException {
		FunctionDTO function = new FunctionDTO();
		try {
			function = functionAssembler.modelToDto(functionDao.findById(functionId));
			return function;
		} catch (DaoException e) {
			logger.error("Error while finding the function by id" + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
	@Override
	public List<FunctionDTO> getFunctions() throws RemoteException {
		List<FunctionDTO> functionsDTOlist = new ArrayList<>();
		try {
			List<Function> list = functionDao.getFunctions();
			for (Function function : list) {
				FunctionDTO functionDto = functionAssembler.modelToDto(function);
				functionsDTOlist.add(functionDto);
			}
			return functionsDTOlist;
		} catch (DaoException e) {
			logger.error("Error while retrieving the functions " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
