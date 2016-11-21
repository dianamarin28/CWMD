package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.Department;
import edu.ubb.cwmdEjb.model.Function;
import edu.ubb.cwmdEjbClient.dtos.DepartmentDTO;
import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;

public class DepartmentAssembler {

	public DepartmentAssembler() {
	}

	public Department dtoToModel(DepartmentDTO departmentDto) {
		if (departmentDto == null) {
			return null;
		} else {
			Department department = new Department();

			department.setUuid(departmentDto.getUuid());
			department.setId(departmentDto.getDepartmentId());
			department.setName(departmentDto.getName());

			FunctionAssembler functionAssembler = new FunctionAssembler();
			List<Function> functions = new ArrayList<>();
			for (FunctionDTO function : departmentDto.getFunctions()) {
				functions.add(functionAssembler.dtoToModelSimple(function));
			}
			department.setFunctions(functions);

			return department;
		}
	}

	public Department dtoToModelSimple(DepartmentDTO departmentDto) {
		if (departmentDto == null) {
			return null;
		} else {
			Department department = new Department();

			department.setUuid(departmentDto.getUuid());
			department.setId(departmentDto.getDepartmentId());
			department.setName(departmentDto.getName());

			// the functions are not set

			return department;
		}
	}

	public DepartmentDTO modelToDto(Department department) {
		if (department == null) {
			return null;
		} else {
			DepartmentDTO departmentDto = new DepartmentDTO();

			departmentDto.setUuid(department.getUuid());
			departmentDto.setDepartmentId(department.getId());
			departmentDto.setName(department.getName());

			FunctionAssembler functionAssembler = new FunctionAssembler();
			List<FunctionDTO> functions = new ArrayList<>();
			for (Function function : department.getFunctions()) {
				functions.add(functionAssembler.modelToDtoSimple(function));
			}
			departmentDto.setFunctions(functions);

			return departmentDto;
		}
	}

	public DepartmentDTO modelToDtoSimple(Department department) {
		if (department == null) {
			return null;
		} else {
			DepartmentDTO departmentDto = new DepartmentDTO();

			departmentDto.setUuid(department.getUuid());
			departmentDto.setDepartmentId(department.getId());
			departmentDto.setName(department.getName());

			// the functions are not set

			return departmentDto;
		}
	}

}
