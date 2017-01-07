package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.ActiveFlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;

@ManagedBean
@ViewScoped
public class FinishedTasksAreaBean implements Serializable {
	private static final long serialVersionUID = -1165337396429332712L;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private ActiveFlowBeanInterface activeFlowBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	@EJB
	private FunctionBeanInterface functionBeanInterface;

	private UserDTO userDTO;

	private List<ActiveFlowDTO> activeFlowDTOs = new ArrayList<>();

	private List<VersionDTO> versionDTOs = new ArrayList<>();

	private ActiveFlowDTO selectedActiveFlow;

	private List<FunctionDTO> functionsDTOs = new ArrayList<>();

	private VersionDTO versionDTO;

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userName = session.getAttribute("userName").toString();
		userDTO = userBeanInterface.getByUserName(userName);
		String userRole = session.getAttribute("userRole").toString();

		// get the active flows of a user
		if (userRole.equals("administrator")) {
			activeFlowDTOs = activeFlowBeanInterface.getFinishedForAdministrator(userDTO.getUserId());
		} else {
			activeFlowDTOs = activeFlowBeanInterface.getFinishedByUserId(userDTO.getUserId());
		}
	}

	public void setValues() {
		versionDTOs = versionBeanInterface.getAllVersionsFromActiveFlow(selectedActiveFlow.getActiveFlowId());

		// functionsDTOs = selectedActiveFlow.getFlow().getFunctions();
		functionsDTOs = functionBeanInterface.getFunctionsByFlow(selectedActiveFlow.getFlow().getFlowId());
	}

	public List<ActiveFlowDTO> getActiveFlowDTOs() {
		return activeFlowDTOs;
	}

	public void setActiveFlowDTOs(List<ActiveFlowDTO> activeFlowDTOs) {
		this.activeFlowDTOs = activeFlowDTOs;
	}

	public List<VersionDTO> getVersionDTOs() {
		return versionDTOs;
	}

	public void setVersionDTOs(List<VersionDTO> versionDTOs) {
		this.versionDTOs = versionDTOs;
	}

	public ActiveFlowDTO getSelectedActiveFlow() {
		return selectedActiveFlow;
	}

	public void setSelectedActiveFlow(ActiveFlowDTO selectedActiveFlow) {
		this.selectedActiveFlow = selectedActiveFlow;
	}

	public List<FunctionDTO> getFunctionsDTOs() {
		return functionsDTOs;
	}

	public void setFunctionsDTOs(List<FunctionDTO> functionsDTOs) {
		this.functionsDTOs = functionsDTOs;
	}

	public VersionDTO getVersionDTO() {
		return versionDTO;
	}

	public void setVersionDTO(VersionDTO versionDTO) {
		this.versionDTO = versionDTO;
	}

}
