package edu.ubb.cwmdEjb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@Column(name = "fileName", nullable = false)
	private String fileName;

	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}