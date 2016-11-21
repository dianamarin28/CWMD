package edu.ubb.cwmdEjbClient.dtos;

import java.util.List;

public class ActiveFlowDTO {

	private Long activeFlowId;

	private String uuid;

	private FlowDTO flow;

	private UserDTO user;

	private List<DocumentDTO> documents;

	public Long getActiveFlowId() {
		return activeFlowId;
	}

	public void setActiveFlowId(Long activeFlowId) {
		this.activeFlowId = activeFlowId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public FlowDTO getFlow() {
		return flow;
	}

	public void setFlow(FlowDTO flow) {
		this.flow = flow;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<DocumentDTO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}

}
