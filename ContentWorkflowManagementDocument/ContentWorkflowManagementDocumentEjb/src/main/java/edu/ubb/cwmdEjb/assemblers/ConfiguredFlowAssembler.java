package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ConfiguredFlow;
import edu.ubb.cwmdEjbClient.dtos.ConfiguredFlowDTO;
import edu.ubb.cwmdEjb.model.Flow;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;

public class ConfiguredFlowAssembler {

	public ConfiguredFlowAssembler() {
	}

	public ConfiguredFlow dtoToModel(ConfiguredFlowDTO configuredFlowDto) {
		if (configuredFlowDto == null) {
			return null;
		} else {
			ConfiguredFlow configuredFlow = new ConfiguredFlow();

			configuredFlow.setUuid(configuredFlowDto.getUuid());
			configuredFlow.setId(configuredFlowDto.getConfiguredFlowId());
			
			configuredFlow.setP1(configuredFlowDto.getP1());
			configuredFlow.setP2(configuredFlowDto.getP2());
			configuredFlow.setP3(configuredFlowDto.getP3());
			configuredFlow.setP4(configuredFlowDto.getP4());
			configuredFlow.setP5(configuredFlowDto.getP5());
			configuredFlow.setP6(configuredFlowDto.getP6());
			configuredFlow.setP7(configuredFlowDto.getP7());
			
			FlowAssembler flowAssembler = new FlowAssembler();
			List<Flow> flows = new ArrayList<>();
			if (configuredFlowDto.getFlows() != null) {
				for (FlowDTO flowDto : configuredFlowDto.getFlows()) {
					flows.add(flowAssembler.dtoToModel(flowDto));
				}
			}
			configuredFlow.setFlows(flows);			

			return configuredFlow;
		}
	}

	public ConfiguredFlow dtoToModelSimple(ConfiguredFlowDTO configuredFlowDto) {
		if (configuredFlowDto == null) {
			return null;
		} else {
			ConfiguredFlow configuredFlow = new ConfiguredFlow();

			configuredFlow.setUuid(configuredFlowDto.getUuid());
			configuredFlow.setId(configuredFlowDto.getConfiguredFlowId());

			configuredFlow.setP1(configuredFlowDto.getP1());
			configuredFlow.setP2(configuredFlowDto.getP2());
			configuredFlow.setP3(configuredFlowDto.getP3());
			configuredFlow.setP4(configuredFlowDto.getP4());
			configuredFlow.setP5(configuredFlowDto.getP5());
			configuredFlow.setP6(configuredFlowDto.getP6());
			configuredFlow.setP7(configuredFlowDto.getP7());
			
			// the map of functions is not set

			// the set of active ConfiguredFlows is not set

			return configuredFlow;
		}
	}

	public ConfiguredFlowDTO modelToDto(ConfiguredFlow configuredFlow) {
		if (configuredFlow == null) {
			return null;
		} else {
			ConfiguredFlowDTO configuredFlowDto = new ConfiguredFlowDTO();

			configuredFlowDto.setUuid(configuredFlow.getUuid());
			configuredFlowDto.setConfiguredFlowId(configuredFlow.getId());
			
			configuredFlowDto.setP1(configuredFlow.getP1());
			configuredFlowDto.setP2(configuredFlow.getP2());
			configuredFlowDto.setP3(configuredFlow.getP3());
			configuredFlowDto.setP4(configuredFlow.getP4());
			configuredFlowDto.setP5(configuredFlow.getP5());
			configuredFlowDto.setP6(configuredFlow.getP6());
			configuredFlowDto.setP7(configuredFlow.getP7());
			
			FlowAssembler flowAssembler = new FlowAssembler();
			List<FlowDTO> flowDtos = new ArrayList<>();
			if (configuredFlow.getFlows() != null) {
				for (Flow flow : configuredFlow.getFlows()) {
					flowDtos.add(flowAssembler.modelToDtoSimple(flow));
				}
			}
			configuredFlowDto.setFlows(flowDtos);
			return configuredFlowDto;
		}
	}

	public ConfiguredFlowDTO modelToDtoSimple(ConfiguredFlow configuredFlow) {
		if (configuredFlow == null) {
			return null;
		} else {
			ConfiguredFlowDTO configuredFlowDto = new ConfiguredFlowDTO();

			configuredFlowDto.setUuid(configuredFlow.getUuid());
			configuredFlowDto.setConfiguredFlowId(configuredFlow.getId());
			
			configuredFlowDto.setP1(configuredFlow.getP1());
			configuredFlowDto.setP2(configuredFlow.getP2());
			configuredFlowDto.setP3(configuredFlow.getP3());
			configuredFlowDto.setP4(configuredFlow.getP4());
			configuredFlowDto.setP5(configuredFlow.getP5());
			configuredFlowDto.setP6(configuredFlow.getP6());
			configuredFlowDto.setP7(configuredFlow.getP7());
			
			return configuredFlowDto;
		}
	}

}
