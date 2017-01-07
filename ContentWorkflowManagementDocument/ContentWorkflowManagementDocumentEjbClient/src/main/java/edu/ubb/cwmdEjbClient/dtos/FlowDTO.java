package edu.ubb.cwmdEjbClient.dtos;

import java.util.List;

public class FlowDTO {

	private Long flowId;

	private String uuid;

	private String name;

	private List<FunctionDTO> functions;

	private List<ActiveFlowDTO> activeFlows;

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

	public List<ActiveFlowDTO> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlowDTO> activeFlows) {
		this.activeFlows = activeFlows;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
