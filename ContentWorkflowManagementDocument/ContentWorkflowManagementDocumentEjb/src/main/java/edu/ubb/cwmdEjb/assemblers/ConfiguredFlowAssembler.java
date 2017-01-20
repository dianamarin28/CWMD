package edu.ubb.cwmdEjb.assemblers;

import edu.ubb.cwmdEjb.model.ConfiguredFlow;
import edu.ubb.cwmdEjbClient.dtos.ConfiguredFlowDTO;

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

			FlowAssembler flowAssembler = new FlowAssembler();
			configuredFlow.setFlow(flowAssembler.dtoToModelSimple(configuredFlowDto.getFlow()));

			
			configuredFlow.setP1(configuredFlowDto.getP1());
			configuredFlow.setP2(configuredFlowDto.getP2());
			configuredFlow.setP3(configuredFlowDto.getP3());
			configuredFlow.setP4(configuredFlowDto.getP4());
			configuredFlow.setP5(configuredFlowDto.getP5());
			configuredFlow.setP6(configuredFlowDto.getP6());
			configuredFlow.setP7(configuredFlowDto.getP7());
			

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

			FlowAssembler flowAssembler = new FlowAssembler();
			configuredFlow.setFlow(flowAssembler.dtoToModelSimple(configuredFlowDto.getFlow()));
			
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

			FlowAssembler flowAssembler = new FlowAssembler();
			configuredFlowDto.setFlow(flowAssembler.modelToDtoSimple(configuredFlow.getFlow()));
			
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

	public ConfiguredFlowDTO modelToDtoSimple(ConfiguredFlow configuredFlow) {
		if (configuredFlow == null) {
			return null;
		} else {
			ConfiguredFlowDTO configuredFlowDto = new ConfiguredFlowDTO();

			configuredFlowDto.setUuid(configuredFlow.getUuid());
			configuredFlowDto.setConfiguredFlowId(configuredFlow.getId());

			FlowAssembler flowAssembler = new FlowAssembler();
			configuredFlowDto.setFlow(flowAssembler.modelToDtoSimple(configuredFlow.getFlow()));
			
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
