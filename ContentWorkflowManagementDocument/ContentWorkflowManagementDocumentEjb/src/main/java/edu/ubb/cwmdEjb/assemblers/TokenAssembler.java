package edu.ubb.cwmdEjb.assemblers;

import edu.ubb.cwmdEjb.model.Token;
import edu.ubb.cwmdEjbClient.dtos.TokenDTO;

public class TokenAssembler {

	public TokenAssembler() {

	}

	public Token dtoToModel(TokenDTO tokenDto) {
		if (tokenDto == null) {
			return null;
		} else {
			Token token = new Token();

			token.setUuid(tokenDto.getUuid());
			token.setId(token.getId());
			UserAssembler userAssembler = new UserAssembler();
			token.setUser(userAssembler.dtoToModelSimple(tokenDto.getUser()));
			token.setValue(tokenDto.getValue());
			token.setCreationDate(token.getCreationDate());

			return token;
		}
	}

	public TokenDTO modelToDto(Token token) {
		if (token == null) {
			return null;
		} else {
			TokenDTO tokenDto = new TokenDTO();

			tokenDto.setUuid(token.getUuid());
			tokenDto.setTokenId(token.getId());
			UserAssembler userAssembler = new UserAssembler();
			tokenDto.setUser(userAssembler.modelToDtoSimple(token.getUser()));
			tokenDto.setValue(token.getValue());
			tokenDto.setCreationDate(token.getCreationDate());

			return tokenDto;
		}
	}
}
