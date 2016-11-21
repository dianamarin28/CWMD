package edu.ubb.cwmdEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.ubb.cwmdEjb.model.ActiveFlow;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.model.UserRole;
import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.DocumentDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;

public class UserAssembler {

	public UserAssembler() {
	}

	public User dtoToModel(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();

			user.setUuid(dto.getUuid());
			user.setId(dto.getUserId());
			user.setFirstName(dto.getFirstName());
			user.setUserName(dto.getUserName());
			user.setLastName(dto.getLastName());
			user.setPassword(dto.getPassword());
			user.setUserRole(UserRole.valueOf(dto.getUserRole()));
			user.setEmail(dto.getEmail());

			FunctionAssembler functionAssembler = new FunctionAssembler();
			user.setFunction(functionAssembler.dtoToModelSimple(dto.getFunction()));

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlow> activeFlows = new ArrayList<>();
			for (ActiveFlowDTO activeFlow : dto.getActiveFlows()) {
				activeFlows.add(activeFlowAssembler.dtoToModelSimple(activeFlow));
			}
			user.setActiveFlows(activeFlows);

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<Document> documents = new ArrayList<>();
			for (DocumentDTO document : dto.getDocuments()) {
				documents.add(documentAssembler.dtoToModelSimple(document));
			}
			user.setDocuments(documents);

			return user;
		}

	}

	public User dtoToModelSimple(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();

			user.setUuid(dto.getUuid());
			user.setId(dto.getUserId());
			user.setFirstName(dto.getFirstName());
			user.setUserName(dto.getUserName());
			user.setLastName(dto.getLastName());
			user.setPassword(dto.getPassword());
			user.setUserRole(UserRole.valueOf(dto.getUserRole()));
			user.setEmail(dto.getEmail());

			FunctionAssembler fa = new FunctionAssembler();
			user.setFunction(fa.dtoToModelSimple(dto.getFunction()));

			// the list of active flows is not set

			// the list of documents is not set

			return user;
		}
	}

	public UserDTO modelToDto(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO userDTO = new UserDTO();

			userDTO.setUuid(user.getUuid());
			userDTO.setUserId(user.getId());
			userDTO.setFirstName(user.getFirstName());
			userDTO.setUserName(user.getUserName());
			userDTO.setLastName(user.getLastName());
			userDTO.setPassword(user.getPassword());
			userDTO.setUserRole(user.getUserRole().toString());
			userDTO.setEmail(user.getEmail());

			FunctionAssembler functionAssembler = new FunctionAssembler();
			userDTO.setFunction(functionAssembler.modelToDtoSimple(user.getFunction()));

			ActiveFlowAssembler activeFlowAssembler = new ActiveFlowAssembler();
			List<ActiveFlowDTO> activeFlows = new ArrayList<>();
			for (ActiveFlow activeFlow : user.getActiveFlows()) {
				activeFlows.add(activeFlowAssembler.modelToDtoSimple(activeFlow));
			}
			userDTO.setActiveFlows(activeFlows);

			DocumentAssembler documentAssembler = new DocumentAssembler();
			List<DocumentDTO> documents = new ArrayList<>();
			for (Document document : user.getDocuments()) {
				documents.add(documentAssembler.modelToDtoSimple(document));
			}
			userDTO.setDocuments(documents);

			return userDTO;
		}
	}

	public UserDTO modelToDtoSimple(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO userDTO = new UserDTO();

			userDTO.setUuid(user.getUuid());
			userDTO.setUserId(user.getId());
			userDTO.setFirstName(user.getFirstName());
			userDTO.setUserName(user.getUserName());
			userDTO.setLastName(user.getLastName());
			userDTO.setPassword(user.getPassword());
			userDTO.setUserRole(user.getUserRole().toString());
			userDTO.setEmail(user.getEmail());

			userDTO.setFunction(new FunctionAssembler().modelToDtoSimple(user.getFunction()));

			// the list of active flows is not set

			// the list of documents is not set

			return userDTO;
		}
	}

}
