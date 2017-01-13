package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.FunctionBeanInterface;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;
import edu.ubb.cwmdWeb.util.EmailHelper;

@ManagedBean
@ViewScoped
public class ManageUsers implements Serializable {

	private static final long serialVersionUID = 3896346608947041187L;

	@EJB
	private UserBeanInterface userBeanInterface;
	
	@EJB
	private FunctionBeanInterface functionBeanInterface;

	private List<UserDTO> userList;

	private UserDTO userDTO;

	private String firstName;

	private String userName;

	private String lastName;

	private String password1;

	private String password2;

	private String userRole;

	private List<String> userRoles = new ArrayList<>();

	private String email;

	private FunctionDTO function;

	
	private List<FunctionDTO> functionsList = new ArrayList<>();

	@PostConstruct
	public void initBean() {
		 FacesContext facesContext = FacesContext.getCurrentInstance();
		 HttpSession session = (HttpSession)
		 facesContext.getExternalContext().getSession(false);

		
		 String userName = session.getAttribute("userName").toString();

		userRoles = new ArrayList<>(Arrays.asList("ADMINISTRATOR", "CONTRIBUTOR", "MANAGER", "READER"));
		
		//get user that belong to a department///
		
		UserDTO user =userBeanInterface.getByUserName(userName);
		Long depId = user.getFunction().getDepartment().getDepartmentId();

		String departmentName = user.getFunction().getDepartment().getName();
		userList = userBeanInterface.getUserFromDepartment(depId);
		
	functionsList = functionBeanInterface.getFunctionsByDepartment(departmentName);
	}

	public UserDTO getUserById(Long id) {
		UserDTO u = new UserDTO();
		try {
			u = userBeanInterface.findById(id);
		} catch (RemoteException e) {

		}
		return u;
	}

	public void restoreFields(UserDTO selected) {
		UserDTO a = getUserById(selected.getUserId());
		selected.setEmail(a.getEmail());
		selected.setFirstName(a.getFirstName());
		selected.setFunction(a.getFunction());
		selected.setLastName(a.getLastName());
		selected.setUserName(a.getUserName());
		selected.setUserRole(a.getUserRole());
	}

	public UserBeanInterface getUserBeanInterface() {
		return userBeanInterface;
	}

	public void onRowEdit(RowEditEvent event) {

		UserDTO selectedUser = (UserDTO) event.getObject();
		String userName = getUserById(selectedUser.getUserId()).getUserName();
		if (!userName.equals(selectedUser.getUserName())
				&& userBeanInterface.getByUserName(selectedUser.getUserName()) != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User Name already exits"));
			restoreFields(selectedUser);
			return;
		}

		try {

			userBeanInterface.updateUser(selectedUser, false);

		} catch (RemoteException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while updating the user"));
		}

		FacesMessage msg = new FacesMessage("User was edited:", selectedUser.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit was cancelled for: ", ((UserDTO) event.getObject()).getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String processInsert() {

		if (!password1.equals(password2)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Passwords do not match"));
		} else {
			if (userBeanInterface.getByUserName(userName) != null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User Name already exists"));
			} else if(userRole == null || userRole.equals("")){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User Role can not be empty"));
				
			}else if(function == null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Function can not be empty"));
				
			}else {
				userDTO = new UserDTO();
				userDTO.setFirstName(firstName);
				userDTO.setFunction(function);
				userDTO.setLastName(lastName);
				userDTO.setUserName(userName);
				userDTO.setPassword(password1);
				userDTO.setUserRole(userRole);
				userDTO.setEmail(email);

				try {
					userBeanInterface.insertUser(userDTO);
				} catch (RemoteException e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error while inserting the user"));
				}

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "User was inserted"));
			}
		}

		return "ok";
	}
	
	public void rowDelete(UserDTO dto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Info",
				"User" + dto.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		userList.remove(dto);
		try {
			userBeanInterface.deleteUser(dto);
		} catch (RemoteException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
					"Error while deleting"));
		}

	}
	
	public void resetPassword(UserDTO dto) {
		//reset password
		String password = UUID.randomUUID().toString().substring(0, 7);
		dto.setPassword(password);
		userBeanInterface.updateUser(dto, true);
		
		//send email
		String message = "Dear " + dto.getUserName() + ",\n\n" + "Your new password is:" + password + "\n";
		String subject = "Reset password";
		EmailHelper.sendEmail(dto.getEmail(), subject, message);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Info",
				"Password was reseted");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
	}

	public void setUserBeanInterface(UserBeanInterface userBeanInterface) {
		this.userBeanInterface = userBeanInterface;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FunctionDTO getFunction() {
		return function;
	}

	public void setFunction(FunctionDTO function) {
		this.function = function;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public List<FunctionDTO> getFunctionsList() {
		return functionsList;
	}

	public void setFunctionsList(List<FunctionDTO> functionsList) {
		this.functionsList = functionsList;
	}

}
