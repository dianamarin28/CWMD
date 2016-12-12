package edu.ubb.cwmdWeb.backingBeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TemplateBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;
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

	private TemplateDTO template;

	private List<TemplateDTO> templates = new ArrayList<>();

	private String name;

	private String docAbstract;

	private String keyWords;

	private String userName;

	private DocumentDTO documentDTO;

	private VersionDTO versionDTO;

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		userName = session.getAttribute("userName").toString();

		// get all the templates
		templates = templateBeanInterface.getTemplates();
	}

	public String insertDocument() {
		UserDTO userDTO = userBeanInterface.getByUserName(userName);
		if (documentBeanInterface.getByNameAndUserId(name, userDTO.getUserId()) != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"You have already created a document with this name"));
		} else if (versionDTO == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "A file has to be uploaded"));
		} else if (name == null || name.equals("")) {
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

}
