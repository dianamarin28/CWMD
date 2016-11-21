package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "flow")
public class Flow extends BaseEntity {

	private static final long serialVersionUID = -1655899541673186225L;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "flows")
	private List<Function> functions;

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

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public List<ActiveFlow> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlow> activeFlows) {
		this.activeFlows = activeFlows;
	}

}
