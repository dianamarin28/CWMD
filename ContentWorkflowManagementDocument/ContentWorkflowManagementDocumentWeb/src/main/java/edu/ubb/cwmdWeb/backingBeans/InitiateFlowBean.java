package edu.ubb.cwmdWeb.backingBeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;

import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class InitiateFlowBean  implements Serializable{
	private static final long serialVersionUID = -6123801107083963826L;
	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	@EJB
	private DocumentBeanInterface documentBeanInterface;
	
	@EJB
	private FlowBeanInterface flowBeanInterface;
	
	private String userName;

	private List<DocumentDTO> documents = new ArrayList<>();
	
	private DocumentDTO chosenDocument;
	private VersionDTO chosenVersion;
	private List<VersionDTO> versions = new ArrayList<>();
	private List<VersionDTO> versionsOfDocument = new ArrayList<>();
	private Long userId;
	private FlowDTO flowType;
	private List<FlowDTO> flows = new ArrayList<>();
	
	
	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		userName = session.getAttribute("userName").toString();
		UserDTO userDTO = userBeanInterface.getByUserName(userName);
		userId = userDTO.getUserId();

		documents = documentBeanInterface.getDocumentsForUserID(userId);
		flows = flowBeanInterface.getFlows();
		
		byte[] content = new byte[1];
		content[0] = 0;
		ByteArrayInputStream stream = new ByteArrayInputStream(content);

		stream.mark(0); // remember to this position!
	
	}
	
	public void setChosenDocument(DocumentDTO document){
		this.chosenDocument = document;
	}
	public DocumentDTO getChosenDocument(){
		return this.chosenDocument;
	}
	public void setChosenVersion(VersionDTO version){
		this.chosenVersion = version;
	}
	public VersionDTO getChosenVersion(){
		return this.chosenVersion;
	}
	
	public void setDocumentVersions(){
		versionsOfDocument = versionBeanInterface.getAllVersionsOfDocument(chosenDocument.getDocumentId());
	}
	
	public void setVersionsOfDocument(List<VersionDTO> versions){
		this.versionsOfDocument = versions;
	}
	
	public List<VersionDTO> getVersionsOfDocument(){
		return this.versionsOfDocument;
	}
	
	public void addVersionInCurrentFlow(){
		this.versions.add(this.chosenVersion);
	}
	
	public void setFlowType(FlowDTO flow){
		this.flowType = flow;
	}
	
	public FlowDTO getFlowType(){
		return this.flowType;
	}
	
	public List<FlowDTO> getFlows(){
		return this.flows;
	}
	
	public void setFlows(List<FlowDTO> flows){
		this.flows = flows;
	}
	
	public void startFlow(){
		
	}
	
	
}