package edu.ubb.cwmdWeb.backingBeans;



import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdWeb.util.Constants;

@ManagedBean
@ViewScoped
public class PersonalDataBean implements Serializable {

	private static final long serialVersionUID = 6284186905889653146L;
	private String password1 = null; // new password
	private String password2 = null; // confirmed new password
	private String password = null; // old password/ password to confirm

	private UserDTO user;

	@EJB
	private UserBeanInterface userBean;

	// for session management only
	private FacesContext facesContext;
	private String userN;
	private HttpSession session;

	private String checkPasswords(String password1, String password2) {
		if (!password1.isEmpty() && !password2.isEmpty()) {
			if (password1.equals(password2)) {
				if (password1.length() < 6) {
					return "tooShort";
				}
				return "match"; // passwords are equal
			} else if (!password1.isEmpty() || !password2.isEmpty()) {
				return "differ"; // passwords don't match, but field
									// aren't
				// empty
			}
		}
		return ""; // fields are empty
	}

	public void setNewPassword() {
		user.setPassword(password1);
	}

	public void passwordsDiffer() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_MESSAGE_SUMMARY,
				Constants.PERSONAL_DATA_UNMATCHING_PASSWORDS);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void passwordTooShort() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_MESSAGE_SUMMARY,
				Constants.PERSONAL_DATA_NEW_PASSWORD_TOO_SHORT);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void confirmChanges() {
		// if one of the new password fields isn't empty then a match will be
		// checked
		user.setPassword(password); // old password set by default
		switch (checkPasswords(password1, password2)) {
		case "differ": {
			passwordsDiffer();
			user = userBean.getByUserName(userN);
			return;
		}
		case "tooShort": {
			passwordTooShort();
			user = userBean.getByUserName(userN);
			return;
		}
		case "match": {
			setNewPassword();
			break;
		}
		default:
			break;
		}
		// if new password fields are empty then no new password will be given

		// checking if the user name doesn't exist in the database
		if (!(user.getUserName().equals(userN)) && (userBean.getByUserName(user.getUserName()) != null)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_MESSAGE_SUMMARY,
					Constants.PERSONAL_DATA_USER_NAME_EXISTS);
			FacesContext.getCurrentInstance().addMessage(null, message);
			user = userBean.getByUserName(userN);
			return;
		}
		// checking if current password is correct
		try {
			if (!"".equals(userBean.validateUserNameAndPassword(userN, password))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						Constants.INFORMATION_MESSAGE_SUMMARY, Constants.PERSONAL_DATA_MODIFIED);
				FacesContext.getCurrentInstance().addMessage(null, message);
				// if this point is reached then an update will be committed!
				userBean.updateUser(user, true);
				// update session name with user name
				session.setAttribute("userName", user.getUserName());
				return;
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.WARNING_MESSAGE_SUMMARY,
						Constants.PERSONAL_DATA_INVALID_PASSWORD);
				FacesContext.getCurrentInstance().addMessage(null, message);
				user = userBean.getByUserName(userN);
				return;
			}
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Constants.ERROR_MESSAGE_SUMMARY, Constants.PERSONAL_DATA_UPDATE_ERROR));
		}
	}

	@PostConstruct
	public void init() {
		facesContext = FacesContext.getCurrentInstance();
		session = (HttpSession) facesContext.getExternalContext().getSession(false);
		userN = session.getAttribute("userName").toString();
		try {
			user = userBean.getByUserName(userN);
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR_MESSAGE_SUMMARY, Constants.PERSONAL_DATA_USER_RETRIEVAL_ERROR));
		}

	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}