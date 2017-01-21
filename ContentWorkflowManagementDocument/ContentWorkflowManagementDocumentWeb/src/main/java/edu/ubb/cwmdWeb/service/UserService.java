package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;

@ManagedBean(name = "userService", eager = true)
@ApplicationScoped
public class UserService {

	@EJB
	private UserBeanInterface userBeanInterface;

	public UserDTO getUser(Long id) {
		return userBeanInterface.findById(id);
	}

}
