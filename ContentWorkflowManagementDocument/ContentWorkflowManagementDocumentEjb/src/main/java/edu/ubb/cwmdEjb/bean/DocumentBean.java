package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.DocumentAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.DocumentDAO;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;

@Stateless
@LocalBean
public class DocumentBean implements Serializable, DocumentBeanInterface {

	private static final long serialVersionUID = -4527704268693786405L;

	private static Logger logger = LoggerFactory.getLogger(DocumentBean.class);

	@EJB
	private DocumentDAO documentDAO;

	private DocumentAssembler documentAssembler = new DocumentAssembler();

	@Override
	public void insertDocument(DocumentDTO documentDTO) throws RemoteException {
		Document document = documentAssembler.dtoToModel(documentDTO);
		try {
			documentDAO.insertDocument(document);
		} catch (DaoException e) {
			logger.error("Document insertion error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public DocumentDTO getByNameAndUserId(String documentName, Long userId) throws RemoteException {
		try {
			DocumentDTO documentDTO = documentAssembler
					.modelToDtoSimple(documentDAO.getByNameAndUserId(documentName, userId));
			return documentDTO;
		} catch (DaoException e) {
			logger.error("Get by document name and user id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
