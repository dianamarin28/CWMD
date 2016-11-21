package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class LogOutBean implements Serializable{

	private static final long serialVersionUID = 5136230370565570052L;
	
	private HttpSession getSession() {
		HttpSession session = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext()
				.getSession(false);
		return session;
	}
	
	public String processLogOut() {
		HttpSession session = getSession();
		session.invalidate();
		return "loggedOut";
	}
	

}
