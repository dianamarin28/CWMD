package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public Double getLatestVersionNumberForDraft(Long documentId) throws RemoteException {
		try {
			return versionDAO.getLatestVersionNumberDraft(documentId);
		} catch (DaoException e) {
			logger.error("Version number retrieval failed " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<VersionDTO> getAllVersionsOfDocument(Long documentId) throws RemoteException {
		List<VersionDTO> versionDTOs = new ArrayList<>();
		try {
			List<Version> versions = versionDAO.getAllVersionsOfDocument(documentId);
			for (Version version : versions) {
				versionDTOs.add(versionAssembler.modelToDto(version));
			}
			return versionDTOs;
		} catch (DaoException e) {
			logger.error("Version retrieval failed " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteVersion(VersionDTO versionDTO) throws RemoteException {
		Version version = versionAssembler.dtoToModel(versionDTO);
		try {
			versionDAO.deleteVersion(version);
		} catch (DaoException e) {
			logger.error("Version deletion error: " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public VersionDTO findById(Long versionId) throws RemoteException {
		VersionDTO versionDTO = new VersionDTO();
		try {
			versionDTO = versionAssembler.modelToDto(versionDAO.findById(versionId));
			return versionDTO;
		} catch (DaoException e) {
			logger.error("Find version by id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public Double getLatestVersionNumberForFinal(Long documentId) throws RemoteException {
		try {
			return versionDAO.getLatestVersionNumberFinal(documentId);
		} catch (DaoException e) {
			logger.error("Version number retrieval failed " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<VersionDTO> getAllVersionsFromActiveFlow(Long activeFlowId) throws RemoteException {
		List<VersionDTO> versionDTOs = new ArrayList<>();
		try {
			List<Version> versions = versionDAO.getAllVersionsFromActiveFlow(activeFlowId);
			for (Version version : versions) {
				versionDTOs.add(versionAssembler.modelToDto(version));
			}
			return versionDTOs;
		} catch (DaoException e) {
			logger.error("Version retrieval failed " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
