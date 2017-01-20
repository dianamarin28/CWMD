package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.ActiveFlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;

@ManagedBean
@ViewScoped
public class StopActiveFlowsBean implements Serializable {

	private static final long serialVersionUID = 4509122109675940269L;

	@EJB
	private ActiveFlowBeanInterface activeFlowBeanInterface;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private VersionBeanInterface versionBeanInterface;

	private List<ActiveFlowDTO> activeFlows;

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userName = session.getAttribute("userName").toString();

		UserDTO userDTO = userBeanInterface.getByUserName(userName);

		activeFlows = activeFlowBeanInterface.getActiveForAdministrator(userDTO.getUserId());
	}

	public void delete(ActiveFlowDTO activeFlow) {
		// for all the versions of the active flow set the status to BLOCKED
		try {
			for (VersionDTO version : activeFlow.getVersions()) {
				version.setStatus("BLOCKED");
				versionBeanInterface.updateVersion(version);
			}
			activeFlow.setStatus("REJECTED");
			activeFlowBeanInterface.updateActiveFlow(activeFlow);
			activeFlows.remove(activeFlow);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INOF", "The flow was stopped"));
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error occured while stopping the flow"));
		}
	}

	public List<ActiveFlowDTO> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlowDTO> activeFlows) {
		this.activeFlows = activeFlows;
	}

}
