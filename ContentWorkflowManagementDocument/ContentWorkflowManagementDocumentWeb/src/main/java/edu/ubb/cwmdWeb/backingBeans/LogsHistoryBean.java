package edu.ubb.cwmdWeb.backingBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import edu.ubb.cwmdEjbClient.dtos.LogDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.LogBeanInterface;

@ManagedBean
@ViewScoped
public class LogsHistoryBean implements Serializable {

	private static final long serialVersionUID = 8694608719995899354L;

	// DOCUMENT_DELETION

	private List<LogDTO> documentDeletionLogs = new ArrayList<>();

	// USER_CREATION

	private List<LogDTO> userCreationLogs = new ArrayList<>();

	// PASSWORD_RESET
	private List<LogDTO> passwordResetLogs = new ArrayList<>();

	private LogDTO selectedDocDeletion;

	private LogDTO selectUserCreation;

	private LogDTO selectedPasswordReset;

	@EJB
	private LogBeanInterface logBeanInterface;

	@PostConstruct
	private void initBean() {
		documentDeletionLogs = logBeanInterface.getLogsByType("DOCUMENT_DELETION");
		userCreationLogs = logBeanInterface.getLogsByType("USER_CREATION");
		passwordResetLogs = logBeanInterface.getLogsByType("PASSWORD_RESET");
		adUserName();
	}

	private void adUserName() {
		for (LogDTO log : documentDeletionLogs) {
			if (log.getUserDTO() == null) {
				UserDTO dto = new UserDTO();
				dto.setUserName("System");
				log.setUserDTO(dto);
			}
		}
		for (LogDTO log : userCreationLogs) {
			if (log.getUserDTO() == null) {
				UserDTO dto = new UserDTO();
				dto.setUserName("System");
				log.setUserDTO(dto);
			}
		}
		for (LogDTO log : passwordResetLogs) {
			if (log.getUserDTO() == null) {
				UserDTO dto = new UserDTO();
				dto.setUserName("System");
				log.setUserDTO(dto);
			}
		}
	}

	public List<LogDTO> getDocumentDeletionLogs() {
		return documentDeletionLogs;
	}

	public void setDocumentDeletionLogs(List<LogDTO> documentDeletionLogs) {
		this.documentDeletionLogs = documentDeletionLogs;
	}

	public LogDTO getSelectedDocDeletion() {
		return selectedDocDeletion;
	}

	public void setSelectedDocDeletion(LogDTO selectedDocDeletion) {
		this.selectedDocDeletion = selectedDocDeletion;
	}

	public List<LogDTO> getUserCreationLogs() {
		return userCreationLogs;
	}

	public void setUserCreationLogs(List<LogDTO> userCreationLogs) {
		this.userCreationLogs = userCreationLogs;
	}

	public LogDTO getSelectUserCreation() {
		return selectUserCreation;
	}

	public void setSelectUserCreation(LogDTO selectUserCreation) {
		this.selectUserCreation = selectUserCreation;
	}

	public List<LogDTO> getPasswordResetLogs() {
		return passwordResetLogs;
	}

	public void setPasswordResetLogs(List<LogDTO> passwordResetLogs) {
		this.passwordResetLogs = passwordResetLogs;
	}

	public LogDTO getSelectedPasswordReset() {
		return selectedPasswordReset;
	}

	public void setSelectedPasswordReset(LogDTO selectedPasswordReset) {
		this.selectedPasswordReset = selectedPasswordReset;
	}

}