package edu.ubb.cwmdEjbClient.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class DocumentDTO implements Serializable{


	private static final long serialVersionUID = 1202015588442652893L;

	private Long documentId;

	private String uuid;

	private String name;

	private UserDTO author;

	private TemplateDTO template;

	private List<VersionDTO> versions;

	private LocalDate creationDate;

	private LocalDate lastModficationDate;

	private String docAbstract;

	private String keywords;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
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
		DocumentDTO other = (DocumentDTO) obj;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		return true;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDTO template) {
		this.template = template;
	}

	public List<VersionDTO> getVersions() {
		return versions;
	}

	public void setVersions(List<VersionDTO> versions) {
		this.versions = versions;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getLastModficationDate() {
		return lastModficationDate;
	}

	public void setLastModficationDate(LocalDate lastModficationDate) {
		this.lastModficationDate = lastModficationDate;
	}

	public String getDocAbstract() {
		return docAbstract;
	}

	public void setDocAbstract(String docAbstract) {
		this.docAbstract = docAbstract;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
