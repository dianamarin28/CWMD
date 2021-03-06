package edu.ubb.cwmdEjbClient.dtos;

import java.io.Serializable;
import java.util.List;

public class ConfiguredFlowDTO implements Serializable{
	

	private static final long serialVersionUID = -2911533588389583971L;

	private Long configuredFlowId;

	private String uuid;
	
	private List<FlowDTO> flows;
	
	private String p1;

	private String p2;
	
	private String p3;
	
	private String p4;
	
	private String p5;
	
	private String p6;
	
	private String p7;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<FlowDTO> getFlows() {
		return flows;
	}

	public void setFlows(List<FlowDTO> flows) {
		this.flows = flows;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public String getP6() {
		return p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public Long getConfiguredFlowId() {
		return configuredFlowId;
	}

	public void setConfiguredFlowId(Long configuredFlowId) {
		this.configuredFlowId = configuredFlowId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configuredFlowId == null) ? 0 : configuredFlowId.hashCode());
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
		ConfiguredFlowDTO other = (ConfiguredFlowDTO) obj;
		if (configuredFlowId == null) {
			if (other.configuredFlowId != null)
				return false;
		} else if (!configuredFlowId.equals(other.configuredFlowId))
			return false;
		return true;
	}

}
