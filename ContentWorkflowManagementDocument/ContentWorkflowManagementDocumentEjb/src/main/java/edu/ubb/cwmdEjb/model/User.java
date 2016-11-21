package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

	private static final long serialVersionUID = -7737270588134406230L;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "userName", nullable = false, unique = true)
	private String userName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "userRole")
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@JoinColumn(name = "functionId")
	@ManyToOne
	private Function function;

	@OneToMany(mappedBy = "user")
	private List<ActiveFlow> activeFlows;

	@OneToMany(mappedBy = "author")
	private List<Document> documents;

	public User() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public List<ActiveFlow> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlow> activeFlows) {
		this.activeFlows = activeFlows;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}