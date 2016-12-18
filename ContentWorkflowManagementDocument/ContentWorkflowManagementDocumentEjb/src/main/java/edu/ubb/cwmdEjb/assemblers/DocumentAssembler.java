package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;

public class DocumentAssembler {

	public DocumentAssembler() {
	}

	public Document dtoToModel(DocumentDTO documentDto) {
		if (documentDto == null) {
			return null;
		} else {
			Document document = new Document();

			document.setUuid(documentDto.getUuid());
			document.setId(documentDto.getDocumentFlowId());
			document.setName(documentDto.getName());

			UserAssembler userAssembler = new UserAssembler();
			document.setAuthor(userAssembler.dtoToModelSimple(documentDto.getAuthor()));

			TemplateAssembler templateAssembler = new TemplateAssembler();
			document.setTemplate(templateAssembler.dtoToModelSimple(documentDto.getTemplate()));

			VersionAssembler versionAssembler = new VersionAssembler();
			List<Version> versions = new ArrayList<>();
			if (documentDto.getVersions() != null) {
				for (VersionDTO version : documentDto.getVersions()) {
					versions.add(versionAssembler.dtoToModel(version));
				}
			}
			document.setVersions(versions);

			document.setCreationDate(documentDto.getCreationDate());
			document.setLastModficationDate(documentDto.getLastModficationDate());
			document.setDocAbstract(documentDto.getDocAbstract());
			document.setKeywords(documentDto.getKeywords());

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlow> activeFlows = new ArrayList<>();
			if (documentDto.getActiveFlows() != null) {
				for (ActiveFlowDTO activeFlow : documentDto.getActiveFlows()) {
					activeFlows.add(activeFlowAssembler.dtoToModelSimple(activeFlow));
				}
			}
			document.setActiveFlows(activeFlows);

			return document;
		}
	}

	public Document dtoToModelSimple(DocumentDTO documentDto) {
		if (documentDto == null) {
			return null;
		} else {
			Document document = new Document();

			document.setUuid(documentDto.getUuid());
			document.setId(documentDto.getDocumentFlowId());
			document.setName(documentDto.getName());

			UserAssembler userAssembler = new UserAssembler();
			document.setAuthor(userAssembler.dtoToModelSimple(documentDto.getAuthor()));

			TemplateAssembler templateAssembler = new TemplateAssembler();
			document.setTemplate(templateAssembler.dtoToModelSimple(documentDto.getTemplate()));

			// the list of versions is not set

			document.setCreationDate(documentDto.getCreationDate());
			document.setLastModficationDate(documentDto.getLastModficationDate());
			document.setDocAbstract(documentDto.getDocAbstract());
			document.setKeywords(documentDto.getKeywords());

			// the list of active flows is not set

			return document;
		}
	}

	public DocumentDTO modelToDto(Document document) {
		if (document == null) {
			return null;
		} else {
			DocumentDTO documentDto = new DocumentDTO();

			documentDto.setUuid(document.getUuid());
			documentDto.setDocumentFlowId(document.getId());
			documentDto.setName(document.getName());

			UserAssembler userAssembler = new UserAssembler();
			documentDto.setAuthor(userAssembler.modelToDtoSimple(document.getAuthor()));

			TemplateAssembler templateAssembler = new TemplateAssembler();
			documentDto.setTemplate(templateAssembler.modelToDtoSimple(document.getTemplate()));

			VersionAssembler versionAssembler = new VersionAssembler();
			List<VersionDTO> versions = new ArrayList<>();
			for (Version version : document.getVersions()) {
				versions.add(versionAssembler.modelToDto(version));
			}
			documentDto.setVersions(versions);

			documentDto.setCreationDate(document.getCreationDate());
			documentDto.setLastModficationDate(document.getLastModficationDate());
			documentDto.setDocAbstract(document.getDocAbstract());
			documentDto.setKeywords(document.getKeywords());

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlowDTO> activeFlows = new ArrayList<>();
			for (ActiveFlow activeFlow : document.getActiveFlows()) {
				activeFlows.add(activeFlowAssembler.modelToDtoSimple(activeFlow));
			}
			documentDto.setActiveFlows(activeFlows);

			return documentDto;
		}
	}

	public DocumentDTO modelToDtoSimple(Document document) {
		if (document == null) {
			return null;
		} else {
			DocumentDTO documentDto = new DocumentDTO();

			documentDto.setUuid(document.getUuid());
			documentDto.setDocumentFlowId(document.getId());
			documentDto.setName(document.getName());

			UserAssembler userAssembler = new UserAssembler();
			documentDto.setAuthor(userAssembler.modelToDtoSimple(document.getAuthor()));

			TemplateAssembler templateAssembler = new TemplateAssembler();
			documentDto.setTemplate(templateAssembler.modelToDtoSimple(document.getTemplate()));

			// the set of versions is not set

			documentDto.setCreationDate(document.getCreationDate());
			documentDto.setLastModficationDate(document.getLastModficationDate());
			documentDto.setDocAbstract(document.getDocAbstract());
			documentDto.setKeywords(document.getKeywords());

			// the list of active flows is not set

			return documentDto;
		}
	}

}
