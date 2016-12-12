package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.VersionAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.VersionDAO;
import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;

@Stateless
@LocalBean
public class VersionBean implements Serializable, VersionBeanInterface {

	private static final long serialVersionUID = 2675919509038527611L;

	private static Logger logger = LoggerFactory.getLogger(VersionBean.class);

	@EJB
	private VersionDAO versionDAO;

	private VersionAssembler versionAssembler = new VersionAssembler();

	@Override
	public void insertVersion(VersionDTO versionDTO) throws RemoteException {
		Version version = versionAssembler.dtoToModel(versionDTO);
		try {
			versionDAO.insertVersion(version);
		} catch (DaoException e) {
			logger.error("Version insertion error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
