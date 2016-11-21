package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.Template;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;

public class TemplateAssembler {

	public TemplateAssembler() {
	}

	public Template dtoToModel(TemplateDTO templateDto) {
		if (templateDto == null) {
			return null;
		} else {
			Template template = new Template();

			template.setUuid(templateDto.getUuid());
			template.setId(templateDto.getTemplateId());
			template.setName(templateDto.getName());
			template.setContent(templateDto.getContent());

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<Document> documents = new ArrayList<>();
			for (DocumentDTO document : templateDto.getDocuments()) {
				documents.add(documentAssembler.dtoToModelSimple(document));
			}
			template.setDocuments(documents);

			return template;
		}
	}

	public Template dtoToModelSimple(TemplateDTO templateDto) {
		if (templateDto == null) {
			return null;
		} else {
			Template template = new Template();

			template.setUuid(templateDto.getUuid());
			template.setId(templateDto.getTemplateId());
			template.setName(templateDto.getName());
			template.setContent(templateDto.getContent());

			// the list of document is not set

			return template;
		}
	}

	public TemplateDTO modelToDto(Template template) {
		if (template == null) {
			return null;
		} else {
			TemplateDTO templateDto = new TemplateDTO();

			templateDto.setUuid(template.getUuid());
			templateDto.setTemplateId(template.getId());
			templateDto.setName(template.getName());
			templateDto.setContent(template.getContent());

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<DocumentDTO> documents = new ArrayList<>();
			for (Document document : template.getDocuments()) {
				documents.add(documentAssembler.modelToDtoSimple(document));
			}
			templateDto.setDocuments(documents);

			return templateDto;
		}
	}

	public TemplateDTO modelToDtoSimple(Template template) {
		if (template == null) {
			return null;
		} else {
			TemplateDTO templateDto = new TemplateDTO();

			templateDto.setUuid(template.getUuid());
			templateDto.setTemplateId(template.getId());
			templateDto.setName(template.getName());
			templateDto.setContent(template.getContent());

			// the list of documents is not set

			return templateDto;
		}
	}

}
