package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department extends BaseEntity {

	private static final long serialVersionUID = -8114123503390675522L;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "department")
	private List<Function> functions;

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

}
