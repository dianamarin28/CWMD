package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "function")
public class Function extends BaseEntity {

	private static final long serialVersionUID = -2502548348979454934L;

	@Column(name = "type", nullable = false)
	private String type;

	@OneToMany(mappedBy = "function")
	private List<User> users;

	@ManyToMany()
	@JoinTable(name = "function_flow", joinColumns = {
			@JoinColumn(name = "functionId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "flowId", nullable = false, updatable = false) })
	private List<Flow> flows;

	@JoinColumn(name = "departmentId")
	@ManyToOne()
	private Department department;

	public Function() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

}
