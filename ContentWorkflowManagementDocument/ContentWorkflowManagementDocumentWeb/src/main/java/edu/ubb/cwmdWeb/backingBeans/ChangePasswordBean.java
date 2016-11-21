package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;

@ManagedBean
@ViewScoped
public class ChangePasswordBean implements Serializable {

	private static final long serialVersionUID = 6361102996571333568L;

	@EJB
	private UserBeanInterface userBeanInterface;

	private UserDTO userForChangePassword;

	private String newPassword;

	private String password;

	public String changePassword() {
		try {
			if (password.equals(newPassword)) {
				userForChangePassword.setPassword(newPassword);
				userBeanInterface.changePassword(userForChangePassword);
				FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changed.", "");
				FacesContext.getCurrentInstance().addMessage(null, errorMessage);
				return "changePasswordSuccess";
			} else {
				return "changePasswordFailed";
			}
		} catch (RemoteException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Retype password.", "");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			return "changePasswordFailed";
		}
	}

	@PostConstruct
	public void init() {
		getUserForChangePassword();
	}

	public UserDTO getUserForChangePassword() {
		if (userForChangePassword == null) {
			HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext()
					.getRequest());
			String userName = (String) request.getAttribute("userForChangePassword");
			userForChangePassword = userBeanInterface.getByUserName(userName);
			// userForChangePassword = (UserDTO)
			// request.getAttribute("userForChangePassword");
		}
		return userForChangePassword;
	}

	public void setUserForChangePassword(UserDTO userForChangePassword) {
		this.userForChangePassword = userForChangePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}