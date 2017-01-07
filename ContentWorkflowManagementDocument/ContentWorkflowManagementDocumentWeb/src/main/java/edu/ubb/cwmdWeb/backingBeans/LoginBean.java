package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.ubb.cwmdEjbClient.dtos.TokenDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TokenBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdWeb.util.Constants;
import edu.ubb.cwmdWeb.util.EmailHelper;

@javax.faces.bean.ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1420147975398305049L;

	@EJB
	private UserBeanInterface userBeanInterface;

	@EJB
	private TokenBeanInterface tokenBeanInterface;

	private String userName;
	private String password;
	private String userType = "";
	private static final String OK = "OK";
	private static final String MENUXHTML = "menu.xhtml";
	private static final String MENU = "menu";

	private String email;

	@EJB
	private UserBeanInterface ub;

	public String processLogin() {
		try {
			userType = ub.validateUserNameAndPassword(userName, password).toLowerCase();
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Constants.ERROR_MESSAGE_SUMMARY, Constants.LOGIN_CREDENTIALS_CAN_NOT_BE_VALIDATED));
		}

		if (!userType.isEmpty()) {
			return handleLoginSuccess();
		} else {
			return handleLoginFailed();
		}
	}

	private String handleLoginFailed() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				Constants.ERROR_MESSAGE_SUMMARY, Constants.LOGIN_INVALID_CREDENTIALS));
		userName = "";
		return "loginFailed";
	}

	private String handleLoginSuccess() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, OK, Constants.LOGIN_WELCOME_MESSAGE + userName));
		HttpSession session = getSession();
		session.setAttribute("userRole", userType);
		session.setAttribute("userName", userName);
		session.setAttribute(MENU, userType.replaceAll("\\s", "") + MENUXHTML);

		return "loginOk";
	}

	public String sendEmail(String userName, String email) {
		try {
			TokenDTO tokenDto = tokenBeanInterface.generateToken(userName, email);

			String message = "Dear " + userName
					+ ", \n\n The link to change your password  is: http://localhost:8080/ContentWorkflowManagementDocumentWeb/changePassword.xhtml?token="
					+ tokenDto.getValue() + "\n";
			String subject = "Request to change password";
			// UserDTO tempUser = userBeanInterface.getByUserName(userName);

			EmailHelper.sendEmail(email, subject, message);
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent",
					"The link for reseting the password has been sent");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			return "login.xhtml";
		} catch (RemoteException e) {
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Send email failed",
					"The user name or password is wrong");
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			return "forgotPassword.xhtml";
		}
	}

	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}