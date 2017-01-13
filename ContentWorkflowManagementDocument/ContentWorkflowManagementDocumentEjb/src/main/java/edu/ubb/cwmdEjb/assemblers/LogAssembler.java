package edu.ubb.cwmdEjb.assemblers;

import edu.ubb.cwmdEjb.model.Log;
import edu.ubb.cwmdEjb.model.LogActionType;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjbClient.dtos.LogDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;

public class LogAssembler {

	public LogAssembler() {
	}

	public Log dtoToModel(LogDTO logDTO) {
		if (logDTO == null) {
			return null;
		} else {
			Log log = new Log();

			log.setUuid(logDTO.getUuid());
			log.setId(logDTO.getLogId());
			log.setDate(logDTO.getDate());
			log.setLogActionType(LogActionType.valueOf(logDTO.getLogActionType()));

			UserAssembler userAssembler = new UserAssembler();
			User user = userAssembler.dtoToModelSimple(logDTO.getUserDTO());
			log.setUser(user);

			return log;
		}
	}

	public LogDTO modelToDto(Log log) {
		if (log == null) {
			return null;
		} else {
			LogDTO logDTO = new LogDTO();

			logDTO.setUuid(log.getUuid());
			logDTO.setLogId(log.getId());
			logDTO.setDate(log.getDate());
			logDTO.setLogActionType(log.getLogActionType().toString());

			UserAssembler userAssembler = new UserAssembler();
			UserDTO userDTO = userAssembler.modelToDtoSimple(log.getUser());
			logDTO.setUserDTO(userDTO);

			return logDTO;
		}
	}

}
