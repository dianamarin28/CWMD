package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "template")
public class Template extends BaseEntity {

	private static final long serialVersionUID = -810342193949593172L;

	@Column(name = "name", nullable = false)
	private String name;

	@Lob
	@Column(name = "content", nullable = false)
	private byte[] content;

	@OneToMany(mappedBy = "template")
	private List<Document> documents;

	public Template() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}