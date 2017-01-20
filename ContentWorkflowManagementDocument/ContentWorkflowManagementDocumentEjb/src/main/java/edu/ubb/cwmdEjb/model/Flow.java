package edu.ubb.cwmdEjb.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "flow")
public class Flow extends BaseEntity {

	private static final long serialVersionUID = -1655899541673186225L;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "noOfParticipants", nullable = false)
	private int noOfParticipants;

	// @ManyToMany(mappedBy = "flows")
	// private List<Function> functions;

	@ElementCollection
	@MapKeyColumn(name = "position")
	@Column(name = "functionId")
	@CollectionTable(name = "flow_participants", joinColumns = @JoinColumn(name = "id"))
	Map<String, Long> participants = new HashMap<String, Long>();

	@OneToMany(mappedBy = "flow")
	private List<ActiveFlow> activeFlows;

	public Flow() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ActiveFlow> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlow> activeFlows) {
		this.activeFlows = activeFlows;
	}

	public int getNoOfParticipants() {
		return noOfParticipants;
	}

	public void setNoOfParticipants(int noOfParticipants) {
		this.noOfParticipants = noOfParticipants;
	}

	public Map<String, Long> getParticipants() {
		return participants;
	}

	public void setParticipants(Map<String, Long> participants) {
		this.participants = participants;
	}

}
