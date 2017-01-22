package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.ActiveFlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.DocumentBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;

@ManagedBean
@ViewScoped
public class InitiateFlowBean implements Serializable {

	private static final long serialVersionUID = -6123801107083963826L;

	@EJB
	private FlowBeanInterface flowBeanInterface;

	@EJB
	private DocumentBeanInterface documentBeanInterface;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	@EJB
	private FunctionBeanInterface functionBeanInterface;

	@EJB
	private ActiveFlowBeanInterface activeFlowBeanInterface;

	private String name;

	private FlowDTO flow;

	private List<FlowDTO> flowDTOs = new ArrayList<>();

	private List<DocumentDTO> documents = new ArrayList<>();

	private DocumentDTO document;

	private UserDTO userDTO;

	private DualListModel<VersionDTO> dualListVersion;

	private List<VersionDTO> selectedVersions = new ArrayList<>();

	private List<FunctionDTO> requiredFunctions = new ArrayList<>();

	private DualListModel<UserDTO> dualListUsersFromDepartment;

	private List<UserDTO> usersFromDepartment = new ArrayList<>();

	private Map<String, Long> selectedUsersForFlow = new HashMap<>();

	private List<UserDTO> listSelectedUsers = new ArrayList<>();

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userName = session.getAttribute("userName").toString();
		userDTO = userBeanInterface.getByUserName(userName);

		flowDTOs = flowBeanInterface.getFlows();
		documents = documentBeanInterface.getDocumentsForUserID(userDTO.getUserId());

		usersFromDepartment = userBeanInterface
				.getUserFromDepartment(userDTO.getFunction().getDepartment().getDepartmentId());

		dualListVersion = new DualListModel<VersionDTO>(new ArrayList<>(), new ArrayList<>());
		dualListUsersFromDepartment = new DualListModel<UserDTO>(usersFromDepartment, new ArrayList<>());
	}

	public void valuesForDualList() {
		List<VersionDTO> allVersionsOfDocument = versionBeanInterface
				.getAllVersionsOfDocument(document.getDocumentId());
		List<VersionDTO> finalVersions = new ArrayList<>();
		for (VersionDTO v : allVersionsOfDocument) {
			if (v.getStatus().equals("FINAL")) {
				finalVersions.add(v);
			}
		}
		dualListVersion = new DualListModel<VersionDTO>(finalVersions, new ArrayList<>());
	}

	public void addToSelectedVersion() {
		for (VersionDTO v : dualListVersion.getTarget()) {
			if (!selectedVersions.contains(v)) {
				selectedVersions.add(v);
			}
		}
	}

	public void configureFunctions() {
		requiredFunctions = new ArrayList<>();
		Map<String, Long> functionsInFlow = flow.getParticipants();
		for (String str : functionsInFlow.keySet()) {
			Long currentId = functionsInFlow.get(str);
			FunctionDTO currentFunction = functionBeanInterface.findById(currentId);
			requiredFunctions.add(currentFunction);
		}

	}

	public void submitUsers() {
		listSelectedUsers = new ArrayList<>();
		// check if the users corespond to the functions
		List<UserDTO> selectedUsers = dualListUsersFromDepartment.getTarget();
		Map<String, Long> participantsMap = new HashMap<>();

		Map<String, Long> functionsInFlow = flow.getParticipants();
		for (String str : functionsInFlow.keySet()) {
			Long currentId = functionsInFlow.get(str);
			FunctionDTO currentFunction = functionBeanInterface.findById(currentId);

			// get from the list the user that has this function
			UserDTO currentUser = getUserForFunctionFromList(currentFunction, selectedUsers);
			if (currentUser != null) {
				participantsMap.put(str, userDTO.getUserId());
				listSelectedUsers.add(currentUser);
			}
		}

		if (participantsMap.size() == functionsInFlow.size() && listSelectedUsers.size() == functionsInFlow.size()) {
			// the selected users correspond to all the functions
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The users correspond to the functions"));
			selectedUsersForFlow = participantsMap;

		} else {
			// the selected users do not correspond to all the functions
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"The users do not correspond to the functions"));
			listSelectedUsers = new ArrayList<>();
		}

	}

	private UserDTO getUserForFunctionFromList(FunctionDTO function, List<UserDTO> users) {
		for (UserDTO user : users) {
			if (user.getFunction().equals(function)) {
				return user;
			}
		}
		return null;
	}

	public String startFlow() {
		ActiveFlowDTO activeFlowDTO = new ActiveFlowDTO();

		activeFlowDTO.setName(name);
		activeFlowDTO.setStatus("ACTIVE");
		System.out.println("NAME:  " + name);
		activeFlowDTO.setFlow(flow);
		activeFlowDTO.setParticipants(selectedUsersForFlow);
		activeFlowDTO.setVersions(selectedVersions);
		activeFlowDTO.setUser(userDTO);

		activeFlowDTO.setStep("1");

		try {
			activeFlowBeanInterface.insertActiveFlow(activeFlowDTO);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The active flow was inserted"));
			listSelectedUsers = new ArrayList<>();
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"An error occured while inserting the active flow"));
			listSelectedUsers = new ArrayList<>();
		}
		return "ok";
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FlowDTO getFlow() {
		return flow;
	}

	public void setFlow(FlowDTO flow) {
		this.flow = flow;
	}

	public List<FlowDTO> getFlowDTOs() {
		return flowDTOs;
	}

	public void setFlowDTOs(List<FlowDTO> flowDTOs) {
		this.flowDTOs = flowDTOs;
	}

	public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}

	public DocumentDTO getDocument() {
		return document;
	}

	public void setDocument(DocumentDTO document) {
		this.document = document;
	}

	public DualListModel<VersionDTO> getDualListVersion() {
		return dualListVersion;
	}

	public void setDualListVersion(DualListModel<VersionDTO> dualListVersion) {
		this.dualListVersion = dualListVersion;
	}

	public List<VersionDTO> getSelectedVersions() {
		return selectedVersions;
	}

	public void setSelectedVersions(List<VersionDTO> selectedVersions) {
		this.selectedVersions = selectedVersions;
	}

	public List<FunctionDTO> getRequiredFunctions() {
		return requiredFunctions;
	}

	public void setRequiredFunctions(List<FunctionDTO> requiredFunctions) {
		this.requiredFunctions = requiredFunctions;
	}

	public DualListModel<UserDTO> getDualListUsersFromDepartment() {
		return dualListUsersFromDepartment;
	}

	public void setDualListUsersFromDepartment(DualListModel<UserDTO> dualListUsersFromDepartment) {
		this.dualListUsersFromDepartment = dualListUsersFromDepartment;
	}

	public Map<String, Long> getSelectedUsersForFlow() {
		return selectedUsersForFlow;
	}

	public void setSelectedUsersForFlow(Map<String, Long> selectedUsersForFlow) {
		this.selectedUsersForFlow = selectedUsersForFlow;
	}

	public List<UserDTO> getListSelectedUsers() {
		return listSelectedUsers;
	}

	public void setListSelectedUsers(List<UserDTO> listSelectedUsers) {
		this.listSelectedUsers = listSelectedUsers;
	}

}
