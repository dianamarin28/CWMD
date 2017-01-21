package edu.ubb.cwmdEjbClient.dtos;

import java.io.Serializable;
import java.util.List;

public class FunctionDTO implements Serializable{

	private static final long serialVersionUID = 7214807340554507296L;

	private Long functionId;

	private String uuid;

	private String type;

	private List<UserDTO> users;
	
	private List<FlowDTO> flows;
	
	private DepartmentDTO department;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<FlowDTO> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowDTO> flows) {
		this.flows = flows;
	}

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((functionId == null) ? 0 : functionId.hashCode());
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
		FunctionDTO other = (FunctionDTO) obj;
		if (functionId == null) {
			if (other.functionId != null)
				return false;
		} else if (!functionId.equals(other.functionId))
			return false;
		return true;
	}

}
