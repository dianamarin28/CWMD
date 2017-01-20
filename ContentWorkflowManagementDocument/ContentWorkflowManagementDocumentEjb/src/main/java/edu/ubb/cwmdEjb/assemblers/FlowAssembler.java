package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Flow;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;

public class FlowAssembler {

	public FlowAssembler() {
	}

	public Flow dtoToModel(FlowDTO flowDto) {
		if (flowDto == null) {
			return null;
		} else {
			Flow flow = new Flow();

			flow.setUuid(flowDto.getUuid());
			flow.setId(flowDto.getFlowId());
			flow.setName(flowDto.getName());
			flow.setNoOfParticipants(flowDto.getNoOfParticipants());

			ConfiguredFlowAssembler configuredFlowAssembler = new ConfiguredFlowAssembler();
			flow.setConfiguredFlow(configuredFlowAssembler.dtoToModelSimple(flowDto.getConfiguredFlow()));
// FunctionAssembler functionAssembler = new FunctionAssembler();
			flow.setParticipants(flowDto.getParticipants());
			// List<Function> functions = new ArrayList<>();
			// for (FunctionDTO function : flowDto.getFunctions()) {
			// functions.add(functionAssembler.dtoToModelSimple(function));
			// }
			// flow.setFunctions(functions);

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlow> activeFlows = new ArrayList<>();
			if (flowDto.getActiveFlows() != null) {
				for (ActiveFlowDTO activeFlow : flowDto.getActiveFlows()) {
					activeFlows.add(activeFlowAssembler.dtoToModelSimple(activeFlow));
				}
			}
			flow.setActiveFlows(activeFlows);

			return flow;
		}
	}

	public Flow dtoToModelSimple(FlowDTO flowDto) {
		if (flowDto == null) {
			return null;
		} else {
			Flow flow = new Flow();

			flow.setUuid(flowDto.getUuid());
			flow.setId(flowDto.getFlowId());
			flow.setName(flowDto.getName());
			flow.setNoOfParticipants(flowDto.getNoOfParticipants());

			ConfiguredFlowAssembler configuredFlowAssembler = new ConfiguredFlowAssembler();
			flow.setConfiguredFlow(configuredFlowAssembler.dtoToModelSimple(flowDto.getConfiguredFlow()));

			// the map of functions is not set

			// the set of active flows is not set

			return flow;
		}
	}

	public FlowDTO modelToDto(Flow flow) {
		if (flow == null) {
			return null;
		} else {
			FlowDTO flowDto = new FlowDTO();

			flowDto.setUuid(flow.getUuid());
			flowDto.setFlowId(flow.getId());
			flowDto.setName(flow.getName());
			flowDto.setNoOfParticipants(flow.getNoOfParticipants());

			ConfiguredFlowAssembler configuredFlowAssembler = new ConfiguredFlowAssembler();
			flowDto.setConfiguredFlow(configuredFlowAssembler.modelToDtoSimple(flow.getConfiguredFlow()));

			flowDto.setParticipants(flow.getParticipants());

			// FunctionAssembler functionAssembler = new FunctionAssembler();
			// List<FunctionDTO> functions = new ArrayList<>();
			// for (Function function : flow.getFunctions()) {
			// functions.add(functionAssembler.modelToDtoSimple(function));
			// }
			// flowDto.setFunctions(functions);

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlowDTO> activeFlows = new ArrayList<>();
			if (flow.getActiveFlows() != null) {
				for (ActiveFlow activeFlow : flow.getActiveFlows()) {
					activeFlows.add(activeFlowAssembler.modelToDtoSimple(activeFlow));
				}
			}
			flowDto.setActiveFlows(activeFlows);

			return flowDto;
		}
	}

	public FlowDTO modelToDtoSimple(Flow flow) {
		if (flow == null) {
			return null;
		} else {
			FlowDTO flowDto = new FlowDTO();

			flowDto.setUuid(flow.getUuid());
			flowDto.setFlowId(flow.getId());
			flowDto.setName(flow.getName());
			flowDto.setNoOfParticipants(flow.getNoOfParticipants());

			ConfiguredFlowAssembler configuredFlowAssembler = new ConfiguredFlowAssembler();
			flowDto.setConfiguredFlow(configuredFlowAssembler.modelToDtoSimple(flow.getConfiguredFlow()));

			// the map of functions is not set

			// the list of active flows is not set

			return flowDto;
		}
	}

}
