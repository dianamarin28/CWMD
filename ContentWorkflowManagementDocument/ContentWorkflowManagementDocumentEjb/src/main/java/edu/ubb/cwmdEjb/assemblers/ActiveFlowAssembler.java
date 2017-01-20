package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.ActiveFlowStatus;
import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;

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
			activeFlow.setName(activeFlowDto.getName());
			activeFlow.setStep(activeFlowDto.getStep());
			activeFlow.setStatus(ActiveFlowStatus.valueOf(activeFlowDto.getStatus()));

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlow.setFlow(flowAssembler.dtoToModelSimple(activeFlowDto.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlow.setUser(userAssembler.dtoToModelSimple(activeFlowDto.getUser()));

			VersionAssembler versionAssembler = new VersionAssembler();
			List<Version> versions = new ArrayList<>();
			for (VersionDTO versionDto : activeFlowDto.getVersions()) {
				versions.add(versionAssembler.dtoToModel(versionDto));
			}
			activeFlow.setVersions(versions);

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
			activeFlow.setName(activeFlowDto.getName());
			activeFlow.setStep(activeFlowDto.getStep());
			activeFlow.setStatus(ActiveFlowStatus.valueOf(activeFlowDto.getStatus()));

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
			activeFlowDto.setName(activeFlow.getName());
			activeFlowDto.setStep(activeFlow.getStep());
			activeFlowDto.setStatus(activeFlow.getStatus().toString());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlowDto.setFlow(flowAssembler.modelToDtoSimple(activeFlow.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlowDto.setUser(userAssembler.modelToDtoSimple(activeFlow.getUser()));

			VersionAssembler versionAssembler = new VersionAssembler();
			List<VersionDTO> versionDtos = new ArrayList<>();
			for (Version version : activeFlow.getVersions()) {
				versionDtos.add(versionAssembler.modelToDto(version));
			}
			activeFlowDto.setVersions(versionDtos);

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
			activeFlowDto.setName(activeFlow.getName());
			activeFlowDto.setStep(activeFlow.getStep());
			activeFlowDto.setStatus(activeFlow.getStatus().toString());

			FlowAssembler flowAssembler = new FlowAssembler();
			activeFlowDto.setFlow(flowAssembler.modelToDtoSimple(activeFlow.getFlow()));

			UserAssembler userAssembler = new UserAssembler();
			activeFlowDto.setUser(userAssembler.modelToDtoSimple(activeFlow.getUser()));

			// the list of documents is not set

			return activeFlowDto;
		}
	}
}