package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;

@ManagedBean(name = "functionService", eager = true)
@ApplicationScoped
public class FunctionService {

	@EJB
	private FunctionBeanInterface functionBeanInterface;
	
	public FunctionDTO getFunction(Long id) {
		return functionBeanInterface.findById(id);
	}
}
