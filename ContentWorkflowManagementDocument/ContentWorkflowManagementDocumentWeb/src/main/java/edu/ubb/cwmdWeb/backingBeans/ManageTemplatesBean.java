package edu.ubb.cwmdWeb.backingBeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TemplateBeanInterface;
import edu.ubb.cwmdWeb.util.FileConverter;

@ManagedBean
@ViewScoped
public class ManageTemplatesBean implements Serializable {

	private static final long serialVersionUID = 1260710794078390373L;

	@EJB
	private TemplateBeanInterface templateBeanInterface;

	private String name;

	private TemplateDTO templateDTO;

	private byte[] fileContent;

	private String fileName;

	private List<TemplateDTO> templates = new ArrayList<>();

	@PostConstruct
	public void initBean() {
		templates = templateBeanInterface.getTemplates();
	}

	public String insertTemplate() {
		// TODO : should the template name be unique?
		if (fileContent == null || fileName == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "A file has to be uploaded"));
		} else if (name == null || name.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Name cannot be empty"));
		} else {
			templateDTO = new TemplateDTO();
			templateDTO.setContent(fileContent);
			templateDTO.setFileName(fileName);
			templateDTO.setName(name);

			try {
				templateBeanInterface.insertTemplate(templateDTO);
			} catch (RemoteException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Error occured while inserting the template"));
			}

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The template was inserted"));
		}
		return "ok";
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		try {
			InputStream inputStream = uploadedFile.getInputstream();
			fileContent = FileConverter.convertFileContentToByteArray(inputStream);
			fileName = uploadedFile.getFileName();
		} catch (IOException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload unsuccessful",
					"The file could not be uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		}
	}

	public void delete(TemplateDTO dto) {
		// TODO -> does the deletion of the template have to be logged as well?

		try {
			// only templates that have no documents can be deleted
			if (templateBeanInterface.templateHasDocuments(dto)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "The template  "
						+ dto.getName() + "  cannot be deleted because there are documents that use it");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}

			templateBeanInterface.deleteTemplate(dto);
			templates.remove(dto);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
					"The template " + dto.getName() + " has been deleted ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Error occured while deleting the template"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDTO> templates) {
		this.templates = templates;
	}

}
