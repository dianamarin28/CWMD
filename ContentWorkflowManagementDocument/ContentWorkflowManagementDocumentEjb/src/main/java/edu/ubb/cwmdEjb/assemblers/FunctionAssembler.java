package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.Flow;
import edu.ubb.cwmdEjb.model.Function;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;

public class FunctionAssembler {

	public FunctionAssembler() {
	}

	public Function dtoToModel(FunctionDTO dto) {
		if (dto == null) {
			return null;
		} else {
			Function function = new Function();

			function.setId(dto.getFunctionId());
			function.setUuid(dto.getUuid());
			function.setType(dto.getType());

			List<User> userList = new ArrayList<>();
			UserAssembler userAssembler = new UserAssembler();
			for (UserDTO user : dto.getUsers()) {
				userList.add(userAssembler.dtoToModel(user));
			}
			function.setUsers(userList);

			FlowAssembler flowAssembler = new FlowAssembler();
			List<Flow> flows = new ArrayList<>();
			for (FlowDTO flow : dto.getFlows()) {
				flows.add(flowAssembler.dtoToModelSimple(flow));
			}
			function.setFlows(flows);

			DepartmentAssembler departmentAssembler = new DepartmentAssembler();
			function.setDepartment(departmentAssembler.dtoToModelSimple(dto.getDepartment()));

			return function;
		}

	}

	public Function dtoToModelSimple(FunctionDTO dto) {
		if (dto == null) {
			return null;
		} else {
			Function function = new Function();

			function.setId(dto.getFunctionId());
			function.setUuid(dto.getUuid());
			function.setType(dto.getType());

			// the list of users is not set

			// the list of flows is not set

			DepartmentAssembler departmentAssembler = new DepartmentAssembler();
			function.setDepartment(departmentAssembler.dtoToModelSimple(dto.getDepartment()));

			return function;
		}

	}

	public FunctionDTO modelToDto(Function function) {
		if (function == null) {
			return null;
		} else {
			FunctionDTO functionDTO = new FunctionDTO();

			functionDTO.setUuid(function.getUuid());
			functionDTO.setFunctionId(function.getId());
			functionDTO.setType(function.getType());

			UserAssembler userAssembler = new UserAssembler();
			List<UserDTO> users = new ArrayList<>();
			for (User user : function.getUsers()) {
				users.add(userAssembler.modelToDtoSimple(user));
				// changed form modelToDto
			}
			functionDTO.setUsers(users);

			FlowAssembler flowAssembler = new FlowAssembler();
			List<FlowDTO> flows = new ArrayList<>();
			for (Flow flow : function.getFlows()) {
				flows.add(flowAssembler.modelToDtoSimple(flow));
			}
			functionDTO.setFlows(flows);

			DepartmentAssembler departmentAssembler = new DepartmentAssembler();
			functionDTO.setDepartment(departmentAssembler.modelToDtoSimple(function.getDepartment()));

			return functionDTO;
		}
	}

	public FunctionDTO modelToDtoSimple(Function function) {
		if (function == null) {
			return null;
		} else {
			FunctionDTO functionDTO = new FunctionDTO();

			functionDTO.setUuid(function.getUuid());
			functionDTO.setFunctionId(function.getId());
			functionDTO.setType(function.getType());

			// the list of users is not set

			// the list of flows is not set

			DepartmentAssembler departmentAssembler = new DepartmentAssembler();
			functionDTO.setDepartment(departmentAssembler.modelToDtoSimple(function.getDepartment()));

			return functionDTO;
		}
	}

}
