package edu.ubb.cwmdEjbClient.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FlowDTO implements Serializable {

	private static final long serialVersionUID = 7818135554577168785L;

	private Long flowId;

	private String uuid;

	private String name;

	private int noOfParticipants;

	private ConfiguredFlowDTO configuredFlow;
	// private List<FunctionDTO> functions;

	Map<String, Long> participants;

	private List<ActiveFlowDTO> activeFlows;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flowId == null) ? 0 : flowId.hashCode());
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
		FlowDTO other = (FlowDTO) obj;
		if (flowId == null) {
			if (other.flowId != null)
				return false;
		} else if (!flowId.equals(other.flowId))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConfiguredFlowDTO getConfiguredFlow() {
		return configuredFlow;
	}

	public void setConfiguredFlow(ConfiguredFlowDTO configuredFlow) {
		this.configuredFlow = configuredFlow;
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

	public Map<String, Long> getParticipants() {
		return participants;
	}

	public void setParticipants(Map<String, Long> participants) {
		this.participants = participants;
	}

	public int getNoOfParticipants() {
		return noOfParticipants;
	}

	public void setNoOfParticipants(int noOfParticipants) {
		this.noOfParticipants = noOfParticipants;
	}

}
