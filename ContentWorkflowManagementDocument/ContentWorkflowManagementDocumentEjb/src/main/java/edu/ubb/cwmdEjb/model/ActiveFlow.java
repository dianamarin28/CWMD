package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "activeFlow")
public class ActiveFlow extends BaseEntity {

	private static final long serialVersionUID = 5167730237414597243L;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "step")
	private String step;

	@JoinColumn(name = "flowId")
	@ManyToOne
	private Flow flow;

	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "activeFlow", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Version> versions;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ActiveFlowStatus status;

	public ActiveFlow() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public ActiveFlowStatus getStatus() {
		return status;
	}

	public void setStatus(ActiveFlowStatus status) {
		this.status = status;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}