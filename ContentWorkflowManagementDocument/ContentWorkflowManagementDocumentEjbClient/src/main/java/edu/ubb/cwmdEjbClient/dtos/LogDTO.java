package edu.ubb.cwmdEjbClient.dtos;

import java.time.LocalDate;

public class LogDTO {

	private Long logId;

	private String uuid;

	private UserDTO userDTO;

	private LocalDate date;

	private String logActionType;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLogActionType() {
		return logActionType;
	}

	public void setLogActionType(String logActionType) {
		this.logActionType = logActionType;
	}

}
