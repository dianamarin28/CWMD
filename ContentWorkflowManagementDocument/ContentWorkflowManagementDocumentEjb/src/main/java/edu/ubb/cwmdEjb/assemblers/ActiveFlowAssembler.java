package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;

public class ActiveFlowAssembler {

	public ActiveFlowAssembler() {
	}

	public ActiveFlow dtoToModel(ActiveFlowDTO activeFlowDto) {
		if (activeFlowDto == null) {
			return null;
		} else {
			ActiveFlow activeFlow = new ActiveFlow();

			activeFlow.setUuid(activeFlowDto.getUuid());
			activeFlow.setId(activeFlowDto.getActiveFlowId());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlow.setFlow(flowAssembler.dtoToModelSimple(activeFlowDto.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlow.setUser(userAssembler.dtoToModelSimple(activeFlowDto.getUser()));

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<Document> documents = new ArrayList<>();
			for (DocumentDTO documentDto : activeFlowDto.getDocuments()) {
				documents.add(documentAssembler.dtoToModelSimple(documentDto));
			}
			activeFlow.setDocuments(documents);

			return activeFlow;
		}
	}

	public ActiveFlow dtoToModelSimple(ActiveFlowDTO activeFlowDto) {
		if (activeFlowDto == null) {
			return null;
		} else {
			ActiveFlow activeFlow = new ActiveFlow();

			activeFlow.setUuid(activeFlowDto.getUuid());
			activeFlow.setId(activeFlowDto.getActiveFlowId());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlow.setFlow(flowAssembler.dtoToModelSimple(activeFlowDto.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlow.setUser(userAssembler.dtoToModelSimple(activeFlowDto.getUser()));

			// the list of documents is not set

			return activeFlow;
		}
	}

	public ActiveFlowDTO modelToDto(ActiveFlow activeFlow) {
		if (activeFlow == null) {
			return null;
		} else {
			ActiveFlowDTO activeFlowDto = new ActiveFlowDTO();

			activeFlowDto.setUuid(activeFlow.getUuid());
			activeFlowDto.setActiveFlowId(activeFlow.getId());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlowDto.setFlow(flowAssembler.modelToDtoSimple(activeFlow.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlowDto.setUser(userAssembler.modelToDtoSimple(activeFlow.getUser()));

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<DocumentDTO> documentDtos = new ArrayList<>();
			for (Document document : activeFlow.getDocuments()) {
				documentDtos.add(documentAssembler.modelToDtoSimple(document));
			}
			activeFlowDto.setDocuments(documentDtos);

			return activeFlowDto;
		}
	}

	public ActiveFlowDTO modelToDtoSimple(ActiveFlow activeFlow) {
		if (activeFlow == null) {
			return null;
		} else {
			ActiveFlowDTO activeFlowDto = new ActiveFlowDTO();

			activeFlowDto.setUuid(activeFlow.getUuid());
			activeFlowDto.setActiveFlowId(activeFlow.getId());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlowDto.setFlow(flowAssembler.modelToDtoSimple(activeFlow.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlowDto.setUser(userAssembler.modelToDtoSimple(activeFlow.getUser()));

			// the list of documents is not set

			return activeFlowDto;
		}
	}
}