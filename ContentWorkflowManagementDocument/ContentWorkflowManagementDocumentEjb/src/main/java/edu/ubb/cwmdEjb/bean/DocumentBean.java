package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.DocumentAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.DocumentDAO;
import edu.ubb.cwmdEjb.dao.TemplateDAO;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.Template;
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

	@EJB
	private TemplateDAO templateDAO;

	private DocumentAssembler documentAssembler = new DocumentAssembler();

	@Override
	public void insertDocument(DocumentDTO documentDTO) throws RemoteException {
		Document document = documentAssembler.dtoToModel(documentDTO);
		Long templateId = document.getTemplate().getId();
		Template template = templateDAO.findById(templateId);
		document.setTemplate(template);
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

	@Override
	public List<DocumentDTO> getDocumentsForUserID(Long userId) throws RemoteException {
		try {
			List<DocumentDTO> documentDTOs = new ArrayList<>();
			List<Document> documents = documentDAO.getByUserId(userId);
			for (Document doc : documents) {
				documentDTOs.add(documentAssembler.modelToDtoSimple(doc));
			}
			return documentDTOs;
		} catch (DaoException e) {
			logger.error("Get by document user id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<DocumentDTO> getDocumentsForAdministrator(Long userId) throws RemoteException {
		try {
			List<DocumentDTO> documentDTOs = new ArrayList<>();
			List<Document> documents = documentDAO.getDocumentsForAdministrator(userId);
			for (Document doc : documents) {
				documentDTOs.add(documentAssembler.modelToDtoSimple(doc));
			}
			return documentDTOs;
		} catch (DaoException e) {
			logger.error("Get by document user id error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public DocumentDTO findById(Long documentId) throws RemoteException {
		DocumentDTO doc = new DocumentDTO();
		try {
			doc = documentAssembler.modelToDtoSimple(documentDAO.findById(documentId));
			return doc;
		} catch (DaoException e) {
			logger.error("findById error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public DocumentDTO updateDocument(DocumentDTO documentDTO) throws RemoteException {
		Document document = documentAssembler.dtoToModelSimple(documentDTO);
		try {
			DocumentDTO updatedDTO = documentAssembler.modelToDtoSimple(documentDAO.updateDocument(document));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("Document update error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteDocument(DocumentDTO documentDTO) throws RemoteException {
		Document document = documentAssembler.dtoToModel(documentDTO);
		try {
			documentDAO.deleteDocument(document);
		} catch (DaoException e) {
			logger.error("Document deletion error: " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public List<DocumentDTO> getFinalDocumentsNotInActiveFlows() throws RemoteException {
		List<DocumentDTO> documentsDTO = new ArrayList<>();
		try{
			List<Document> documents = documentDAO.getFinalDocumentsNotInActiveFlows();
			for(Document d : documents){
				DocumentDTO documentDTO = documentAssembler.modelToDtoSimple(d);
				documentsDTO.add(documentDTO);
			}
			return documentsDTO;
		}
		catch(DaoException e){
			logger.error("Get final documents not in active flows error: " +e );
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
