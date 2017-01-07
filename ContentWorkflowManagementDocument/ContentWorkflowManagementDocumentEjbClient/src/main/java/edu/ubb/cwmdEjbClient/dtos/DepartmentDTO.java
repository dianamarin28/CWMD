package edu.ubb.cwmdEjbClient.dtos;

import java.util.List;

public class DepartmentDTO {

	private Long departmentId;

	private String uuid;

	private String name;

	private List<FunctionDTO> functions;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FunctionDTO> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionDTO> functions) {
		this.functions = functions;
	}

}
