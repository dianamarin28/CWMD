package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activeFlow")
public class ActiveFlow extends BaseEntity {

	private static final long serialVersionUID = 5167730237414597243L;

	@JoinColumn(name = "flowId")
	@ManyToOne
	private Flow flow;

	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;

	@ManyToMany()
	@JoinTable(name = "activeFlow_document", joinColumns = {
			@JoinColumn(name = "activeFlowId", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "documentId", nullable = false, updatable = false) })
	private List<Document> documents;

	public ActiveFlow() {
		super();
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

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}