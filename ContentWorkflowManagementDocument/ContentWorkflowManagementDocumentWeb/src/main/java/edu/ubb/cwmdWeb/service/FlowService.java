package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.interfaces.FlowBeanInterface;

@ManagedBean(name = "flowService", eager = true)
@ApplicationScoped
public class FlowService {

	@EJB
	private FlowBeanInterface flowBeanInterface;

	public FlowDTO getFlow(Long id) {
		return flowBeanInterface.findById(id);
	}

}
