package edu.ubb.cwmdEjbClient.dtos;

import java.time.LocalDate;
import java.util.List;

public class DocumentDTO {

	private Long documentFlowId;

	private String uuid;

	private String name;

	private UserDTO author;

	private TemplateDTO template;

	private List<VersionDTO> versions;

	private LocalDate creationDate;

	private LocalDate lastModficationDate;

	private String docAbstract;

	private String keywords;

	private List<ActiveFlowDTO> activeFlows;

	public Long getDocumentFlowId() {
		return documentFlowId;
	}

	public void setDocumentFlowId(Long documentFlowId) {
		this.documentFlowId = documentFlowId;
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

	public List<ActiveFlowDTO> getActiveFlows() {
		return activeFlows;
	}

	public void setActiveFlows(List<ActiveFlowDTO> activeFlows) {
		this.activeFlows = activeFlows;
	}

}
