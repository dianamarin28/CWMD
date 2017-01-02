package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.interfaces.ActiveFlowBeanInterface;

@ManagedBean(name = "activeFlowService", eager = true)
@ApplicationScoped
public class ActiveFlowService {

	@EJB
	private ActiveFlowBeanInterface activeFlowBeanInterface;

	public ActiveFlowDTO getActiveFlow(Long id) {
		return activeFlowBeanInterface.findById(id);
	}

}
