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

import org.primefaces.model.DualListModel;

import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;

@ManagedBean
@ViewScoped
public class ManageFlowsBean implements Serializable {

	private static final long serialVersionUID = 5232970845968873821L;

	@EJB
	private FunctionBeanInterface functionBeanInterface;

	@EJB
	private FlowBeanInterface flowBeanInterface;

	@EJB
	private UserBeanInterface userBeanInterface;

	private List<FunctionDTO> functions = new ArrayList<>();

	private List<FunctionDTO> selectedFunctions = new ArrayList<>();

	private DualListModel<FunctionDTO> dualListFunction;

	private String name;

	private List<FlowDTO> flows = new ArrayList<>();

	@PostConstruct
	public void initBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

		String userName = session.getAttribute("userName").toString();

		UserDTO userDTO = userBeanInterface.getByUserName(userName);

		functions = functionBeanInterface.getFunctionsByDepartment(userDTO.getFunction().getDepartment().getName());
		selectedFunctions = new ArrayList<>();

		dualListFunction = new DualListModel<FunctionDTO>(functions, selectedFunctions);

		flows = flowBeanInterface.getFlows();
	}

	public String insertFlow() {
		if (name == null || name.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "The name cannot be empty"));
		} else if (dualListFunction.getTarget().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "At least a participant must be selected"));
		} else {
			FlowDTO flowDTO = new FlowDTO();

			flowDTO.setName(name);
			List<FunctionDTO> selectedParticipants = dualListFunction.getTarget();
			flowDTO.setNoOfParticipants(selectedParticipants.size());

			int mapIndex = 1;
			Map<String, Long> participantsMap = new HashMap<>();
			for (FunctionDTO f : selectedParticipants) {
				participantsMap.put(String.valueOf(mapIndex), f.getFunctionId());
				mapIndex++;
			}
			flowDTO.setParticipants(participantsMap);

			try {
				flowBeanInterface.insertFlow(flowDTO);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Flow was inserted"));
			} catch (RemoteException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while inserting the flow"));
			}
		}

		return "ok";
	}

	public void delete(FlowDTO flowDTO) {
		if (flowBeanInterface.flowHasActiveFlows(flowDTO)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Active flows use this flow so it cannot be deleted"));
			return;
		}
		try {
			flowBeanInterface.deleteFlow(flowDTO);
			flows.remove(flowDTO);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "The flow was deleted"));
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while deleting the flow"));
		}
	}

	public List<FunctionDTO> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionDTO> functions) {
		this.functions = functions;
	}

	public List<FunctionDTO> getSelectedFunctions() {
		return selectedFunctions;
	}

	public void setSelectedFunctions(List<FunctionDTO> selectedFunctions) {
		this.selectedFunctions = selectedFunctions;
	}

	public DualListModel<FunctionDTO> getDualListFunction() {
		return dualListFunction;
	}

	public void setDualListFunction(DualListModel<FunctionDTO> dualListFunction) {
		this.dualListFunction = dualListFunction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FlowDTO> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowDTO> flows) {
		this.flows = flows;
	}

}
