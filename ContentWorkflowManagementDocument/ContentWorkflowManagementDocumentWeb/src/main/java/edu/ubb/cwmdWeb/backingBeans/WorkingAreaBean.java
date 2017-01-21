package edu.ubb.cwmdWeb.backingBeans;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;
import edu.ubb.cwmdWeb.util.FileConverter;

@ManagedBean
@SessionScoped
public class WorkingAreaBean implements Serializable {
	
	private static final long serialVersionUID = -6123801107083963826L;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	@EJB
	private DocumentBeanInterface documentBeanInterface;

	private String userName;

	private List<DocumentDTO> documents = new ArrayList<>();

	private DocumentDTO viewDocument;

	private VersionDTO viewVersion;

	private String viewDocAbstract;

	private String viewKeyWords;

	private List<VersionDTO> viewVersions = new ArrayList<>();

	private StreamedContent downloadFile;

	private StreamedContent viewFile;

	private Long userId;

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		userName = session.getAttribute("userName").toString();
		UserDTO userDTO = userBeanInterface.getByUserName(userName);
		userId = userDTO.getUserId();

		documents = documentBeanInterface.getDocumentsForUserID(userId);

		byte[] content = new byte[1];
		content[0] = 0;
		ByteArrayInputStream stream = new ByteArrayInputStream(content);

		stream.mark(0); // remember to this position!

		viewFile = new DefaultStreamedContent(stream, "application/pdf");
	}

	public void setViewValues() {
		viewVersions = versionBeanInterface.getAllVersionsOfDocument(viewDocument.getDocumentId());
		viewDocAbstract = viewDocument.getDocAbstract();
		viewKeyWords = viewDocument.getKeywords();
	}

	public void clearViewFields() {
		viewDocAbstract = "";
		viewDocument = null;
		viewKeyWords = "";
		viewVersion = null;
	}

	public DocumentDTO getViewDocument() {
		return viewDocument;
	}

	public void setViewDocument(DocumentDTO viewDocument) {
		this.viewDocument = viewDocument;
	}

	public VersionDTO getViewVersion() {
		return viewVersion;
	}

	public void setViewVersion(VersionDTO viewVersion) {
		this.viewVersion = viewVersion;
	}

	public String getViewDocAbstract() {
		return viewDocAbstract;
	}

	public void setViewDocAbstract(String viewDocAbstract) {
		this.viewDocAbstract = viewDocAbstract;
	}

	public String getViewKeyWords() {
		return viewKeyWords;
	}

	public void setViewKeyWords(String viewKeyWords) {
		this.viewKeyWords = viewKeyWords;
	}

	public List<VersionDTO> getViewVersions() {
		return viewVersions;
	}

	public void setViewVersions(List<VersionDTO> viewVersions) {
		this.viewVersions = viewVersions;
	}

	public List<DocumentDTO> getDocuments() {
		documents = documentBeanInterface.getDocumentsForUserID(userId);
		if (viewDocument != null) {
			viewVersions = versionBeanInterface.getAllVersionsOfDocument(viewDocument.getDocumentId());
		}
		clearViewFields();
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}

	public StreamedContent getDownloadFile() {
		byte[] content = viewVersion.getFileContent();
		InputStream is = FileConverter.convertByteArrayToInputStream(content);
		String fileName = viewVersion.getFileName();

		int positionOfPoint = fileName.indexOf(".");
		String extension = fileName.substring(positionOfPoint, fileName.length());
		downloadFile = new DefaultStreamedContent(is, extension, fileName);
		return downloadFile;
	}

	public void setDownloadFile(StreamedContent downloadFile) {
		this.downloadFile = downloadFile;
	}

	public StreamedContent getViewFile() {
		// if (FacesContext.getCurrentInstance().getRenderResponse()) {
		// return new DefaultStreamedContent();
		// }

		if (viewVersion != null) {
			byte[] content = viewVersion.getFileContent();
			ByteArrayInputStream stream = new ByteArrayInputStream(content);

			stream.mark(0); // remember to this position!
			viewFile = new DefaultStreamedContent(stream, "application/pdf");
		} else {
			byte[] content = new byte[1];
			content[0] = 0;
			ByteArrayInputStream stream = new ByteArrayInputStream(content);

			stream.mark(0); // remember to this position!

			viewFile = new DefaultStreamedContent(stream, "application/pdf");
		}
		if (viewFile != null) {
			try {
				viewFile.getStream().reset();
			} catch (IOException e) {
				System.out.println("view file " + e.getMessage());
			}
		}

		return viewFile;
	}

	public void setViewFile(StreamedContent viewFile) {
		this.viewFile = viewFile;
	}

}
