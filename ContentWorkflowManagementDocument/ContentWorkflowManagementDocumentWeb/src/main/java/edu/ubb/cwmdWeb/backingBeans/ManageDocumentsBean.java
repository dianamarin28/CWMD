package edu.ubb.cwmdWeb.backingBeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.LogDTO;
import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.LogBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TemplateBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;
import edu.ubb.cwmdWeb.util.EmailHelper;
import edu.ubb.cwmdWeb.util.FileConverter;

@ManagedBean
@ViewScoped
public class ManageDocumentsBean implements Serializable {

	private static final long serialVersionUID = 1260611392516431154L;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private TemplateBeanInterface templateBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	@EJB
	private DocumentBeanInterface documentBeanInterface;

	@EJB
	private LogBeanInterface logBeanInterface;

	private TemplateDTO template;

	private List<TemplateDTO> templates = new ArrayList<>();

	private String name;

	private String docAbstract;

	private String keyWords;

	private String userName;

	private DocumentDTO documentDTO;

	private VersionDTO versionDTO;

	private DocumentDTO updateDocument;

	private List<DocumentDTO> documents = new ArrayList<>();

	private VersionDTO updateVersionDTO;

	private String updateDocAbstract;

	private String updateKeyWords;

	private List<String> statuses = new ArrayList<>();

	private String status;

	private TemplateDTO templateForDownload;

	private StreamedContent templateForDownloadFile;

	private DocumentDTO documentForVersion;

	private List<VersionDTO> versionsForUpdate = null;

	private List<String> versionStatuses = new ArrayList<>();

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		userName = session.getAttribute("userName").toString();

		// get all the templates
		templates = templateBeanInterface.getTemplates();
		// set the statuses
		statuses = new ArrayList<>(Arrays.asList("DRAFT", "FINAL"));

		// get all documents (used in update)
		String userRole = session.getAttribute("userRole").toString();
		Long userId = userBeanInterface.getByUserName(userName).getUserId();
		if (userRole.equals("CONTRIBUTOR")) {
			documents = documentBeanInterface.getDocumentsForUserID(userId);
		} else {
			// for administrator
			documents = documentBeanInterface.getDocumentsForAdministrator(userId);
		}

		// for update version
		versionStatuses = new ArrayList<>(Arrays.asList("DRAFT", "FINAL"));
	}

	public String insertDocument() {
		UserDTO userDTO = userBeanInterface.getByUserName(userName);
		if (documentBeanInterface.getByNameAndUserId(name, userDTO.getUserId()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"You have already created a document with this name"));
		} else if (versionDTO == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "A file has to be uploaded"));
		} else if(template == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Template can not be null"));
		}else if (name == null || name.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Name cannot be empty"));
		} else if (docAbstract == null || docAbstract.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Document abstract cannot be empty"));
		} else if (keyWords == null || keyWords.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Key words cannot be empty"));
		} else {
			documentDTO = new DocumentDTO();
			documentDTO.setName(name);
			documentDTO.setAuthor(userDTO);
			documentDTO.setTemplate(template);
			documentDTO.setCreationDate(LocalDate.now());
			documentDTO.setLastModficationDate(LocalDate.now());
			documentDTO.setDocAbstract(docAbstract);
			documentDTO.setKeywords(keyWords);

			try {
				documentBeanInterface.insertDocument(documentDTO);
				DocumentDTO persistedDocument = documentBeanInterface.getByNameAndUserId(documentDTO.getName(),
						userDTO.getUserId());
				versionDTO.setDocument(persistedDocument);
				versionBeanInterface.insertVersion(versionDTO);
			} catch (RemoteException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while inserting the document"));
			}

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The document was inserted"));
		}
		return "ok";
	}

	public void setUpdateValues() {
		updateDocAbstract = updateDocument.getDocAbstract();
		updateKeyWords = updateDocument.getKeywords();
	}

	public void setVersionsTableValues() {
		versionsForUpdate = versionBeanInterface.getAllVersionsOfDocument(documentForVersion.getDocumentId());
	}

	public String updateDocumentMethod() {
		// if (updateVersionDTO == null) {
		// FacesContext.getCurrentInstance().addMessage(null,
		// new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "A file has to
		// be uploaded"));
		// } else
		if (updateDocAbstract.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Document abstract cannot be empty"));
		} else if (updateKeyWords.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Key words cannot be empty"));
		} else {
			updateDocument.setLastModficationDate(LocalDate.now());
			updateDocument.setDocAbstract(updateDocAbstract);
			updateDocument.setKeywords(updateKeyWords);

			try {
				updateDocument = documentBeanInterface.updateDocument(updateDocument);

				if (updateVersionDTO != null) {
					if (status != null && !status.equals("")) {
						Double versionNumber;
						if (status.equals("DRAFT")) {
							versionNumber = versionBeanInterface
									.getLatestVersionNumberForDraft(updateDocument.getDocumentId());
						} else {
							versionNumber = versionBeanInterface
									.getLatestVersionNumberForFinal(updateDocument.getDocumentId());
						}
						if (status.equals("FINAL") && versionNumber < 1) {
							versionNumber = new Double(1);
						} else if (versionNumber < 1) {
							versionNumber += new Double(0.1);
						} else {
							versionNumber += new Double(1);
						}
						updateVersionDTO.setNumber(versionNumber);
						updateVersionDTO.setStatus(status);

						updateVersionDTO.setDocument(updateDocument);
						versionBeanInterface.insertVersion(updateVersionDTO);
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error", "Error while inserting the new version"));
						clearUpdateFields();
						clearModifyVersionsFields();
						return "ok";
					}
				}
			} catch (RemoteException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while updating the document"));
			}

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The document was updated"));
		}
		clearUpdateFields();
		clearModifyVersionsFields();
		return "ok";
	}

	public void clearUpdateFields() {
		updateDocAbstract = "";
		updateDocument = null;
		updateKeyWords = "";
		updateVersionDTO = null;

		status = "";
	}

	public void clearModifyVersionsFields() {
		documentForVersion = null;
		versionsForUpdate = new ArrayList<>();
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		try {
			InputStream inputStream = uploadedFile.getInputstream();
			byte[] fileContent = FileConverter.convertFileContentToByteArray(inputStream);

			versionDTO = new VersionDTO();
			versionDTO.setFileContent(fileContent);
			versionDTO.setFileName(uploadedFile.getFileName());
			versionDTO.setNumber(0.1);
			versionDTO.setStatus("DRAFT");

		} catch (IOException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload unsuccessful",
					"The file could not be uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		}
	}

	public void handleFileUpdate(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		try {
			InputStream inputStream = uploadedFile.getInputstream();
			byte[] fileContent = FileConverter.convertFileContentToByteArray(inputStream);

			updateVersionDTO = new VersionDTO();
			updateVersionDTO.setFileContent(fileContent);
			updateVersionDTO.setFileName(uploadedFile.getFileName());

			// the version number and status are set in update

		} catch (RemoteException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"New version creation unsuccessful", "A new version of the document could not be created.");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		} catch (IOException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload unsuccessful",
					"The file could not be uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		}
	}

	public boolean checkIfAnyVersionInActiveFlow(DocumentDTO documentDTO) {
		List<VersionDTO> versionDTOs = versionBeanInterface.getAllVersionsOfDocument(documentDTO.getDocumentId());
		for (VersionDTO versionDTO : versionDTOs) {
			System.out
					.println("DELETE DOC: " + versionDTO.getVersionId() + " active flow " + versionDTO.getActiveFlow());
			if (versionDTO.getActiveFlow() != null) {
				return true;
			}
		}
		return false;
	}

	public void delete(DocumentDTO dto) {
		if (checkIfAnyVersionInActiveFlow(dto)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"The document cannot be deleted since it belongs in an active flow ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		// get all the versions of the document
		List<VersionDTO> versionDTOs = versionBeanInterface.getAllVersionsOfDocument(dto.getDocumentId());
		try {
			for (VersionDTO versionDTO : versionDTOs) {
				versionBeanInterface.deleteVersion(versionDTO);
			}

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
					"The document " + dto.getName() + " has been deleted ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			documents.remove(dto);

			documentBeanInterface.deleteDocument(dto);
			// send email
			UserDTO author = dto.getAuthor();
			String message = "Dear " + author.getUserName() + ",\n\n" + "Your document with the title " + dto.getName()
					+ " has been deleted.\n";
			String subject = "Document deletion";
			EmailHelper.sendEmail(author.getEmail(), subject, message);

			// log
			UserDTO loggedInUser = userBeanInterface.getByUserName(userName);

			LogDTO logDTO = new LogDTO();
			logDTO.setDate(LocalDate.now());
			logDTO.setLogActionType("DOCUMENT_DELETION");
			logDTO.setUserDTO(loggedInUser);
			logBeanInterface.insertLog(logDTO);

		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while deleting"));
		}

		clearUpdateFields();
		clearModifyVersionsFields();
	}

	public void restoreFields(VersionDTO selectedVersionDTO) {
		try {
			VersionDTO versionDTO = versionBeanInterface.findById(selectedVersionDTO.getVersionId());
			selectedVersionDTO.setFileName(versionDTO.getFileName());
			selectedVersionDTO.setStatus(versionDTO.getStatus());
			selectedVersionDTO.setNumber(versionDTO.getNumber());
		} catch (RemoteException e) {

		}
	}

	public void onRowEdit(RowEditEvent event) {
		VersionDTO selectedVersionDTO = (VersionDTO) event.getObject();
		if (selectedVersionDTO.getFileName() == null || selectedVersionDTO.getFileName().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "The file name cannot be empty"));
			restoreFields(selectedVersionDTO);
			return;
		}

		try {
			if (selectedVersionDTO.getStatus().equals("DRAFT") && selectedVersionDTO.getNumber() >= 1) {
				// if the status is draft, the number must be < 1
				try {
					Double versionNumber = versionBeanInterface
							.getLatestVersionNumberForDraft(selectedVersionDTO.getDocument().getDocumentId());
					versionNumber += 0.1;
					selectedVersionDTO.setNumber(versionNumber);
				} catch (RemoteException e) {
					// no documents with DRAFT status are saved => number = 0.1
					selectedVersionDTO.setNumber(0.1);
				}
			}

			if (selectedVersionDTO.getStatus().equals("FINAL")
					&& Double.compare(selectedVersionDTO.getNumber(), 1) < 0) {
				// if the status is final, the number should be >=1
				try {
					Double versionNumber = versionBeanInterface
							.getLatestVersionNumberForFinal(selectedVersionDTO.getDocument().getDocumentId());
					versionNumber += 1.0;
					selectedVersionDTO.setNumber(versionNumber);
				} catch (RemoteException e) {
					// no documents with FINAL status are saved => number = 1
					selectedVersionDTO.setNumber(new Double(1));
				}
			}

			versionBeanInterface.updateVersion(selectedVersionDTO);
			String message = "v" + ((VersionDTO) event.getObject()).getNumber();
			FacesMessage msg = new FacesMessage("Version was edited:", message);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error occured while updating the version"));
		}

	}

	public void onRowCancel(RowEditEvent event) {
		String message = "v" + ((VersionDTO) event.getObject()).getNumber();
		FacesMessage msg = new FacesMessage("Edit was cancelled for: ", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDTO template) {
		this.template = template;
	}

	public List<TemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDTO> templates) {
		this.templates = templates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocAbstract() {
		return docAbstract;
	}

	public void setDocAbstract(String docAbstract) {
		this.docAbstract = docAbstract;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public DocumentDTO getUpdateDocument() {
		return updateDocument;
	}

	public void setUpdateDocument(DocumentDTO updateDocument) {
		this.updateDocument = updateDocument;
	}

	public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}

	public String getUpdateDocAbstract() {
		return updateDocAbstract;
	}

	public void setUpdateDocAbstract(String updateDocAbstract) {
		this.updateDocAbstract = updateDocAbstract;
	}

	public String getUpdateKeyWords() {
		return updateKeyWords;
	}

	public void setUpdateKeyWords(String updateKeyWords) {
		this.updateKeyWords = updateKeyWords;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TemplateDTO getTemplateForDownload() {
		return templateForDownload;
	}

	public void setTemplateForDownload(TemplateDTO templateForDownload) {
		this.templateForDownload = templateForDownload;
	}

	public StreamedContent getTemplateForDownloadFile() {
		if(templateForDownload == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Template can not be null."));
			return null;
		}
		byte[] content = templateForDownload.getContent();
		System.out.println(
				"GET TEMPLATE: " + templateForDownload.getTemplateId() + " file: " + templateForDownload.getFileName());
		InputStream is = FileConverter.convertByteArrayToInputStream(content);
		String fileName = template.getFileName();

		int pointPosition = fileName.indexOf(".");
		String extension = fileName.substring(pointPosition, fileName.length());
		templateForDownloadFile = new DefaultStreamedContent(is, extension, fileName);

		return templateForDownloadFile;
	}

	public void setTemplateForDownloadFile(StreamedContent templateForDownloadFile) {
		this.templateForDownloadFile = templateForDownloadFile;
	}

	public DocumentDTO getDocumentForVersion() {
		return documentForVersion;
	}

	public void setDocumentForVersion(DocumentDTO documentForVersion) {
		this.documentForVersion = documentForVersion;
	}

	public List<VersionDTO> getVersionsForUpdate() {
		return versionsForUpdate;
	}

	public void setVersionsForUpdate(List<VersionDTO> versionsForUpdate) {
		this.versionsForUpdate = versionsForUpdate;
	}

	public List<String> getVersionStatuses() {
		return versionStatuses;
	}

	public void setVersionStatuses(List<String> versionStatuses) {
		this.versionStatuses = versionStatuses;
	}

}
