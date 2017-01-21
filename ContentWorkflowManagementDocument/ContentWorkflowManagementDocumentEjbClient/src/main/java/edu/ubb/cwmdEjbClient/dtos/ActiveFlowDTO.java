package edu.ubb.cwmdEjbClient.dtos;

import java.io.Serializable;
import java.util.List;

public class ActiveFlowDTO implements Serializable {


	private static final long serialVersionUID = 6719749521602634097L;

	private Long activeFlowId;

	private String uuid;

	private String name;

	private FlowDTO flow;

	private UserDTO user;

	private List<VersionDTO> versions;

	private String status;

	private String step;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeFlowId == null) ? 0 : activeFlowId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActiveFlowDTO other = (ActiveFlowDTO) obj;
		if (activeFlowId == null) {
			if (other.activeFlowId != null)
				return false;
		} else if (!activeFlowId.equals(other.activeFlowId))
			return false;
		return true;
	}

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

	public List<VersionDTO> getVersions() {
		return versions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVersions(List<VersionDTO> versions) {
		this.versions = versions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}
