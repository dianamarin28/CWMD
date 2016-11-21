package edu.ubb.cwmdEjb.assemblers;

import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjbClient.dtos.VersionDTO;

public class VersionAssembler {

	public VersionAssembler() {
	}

	public Version dtoToModel(VersionDTO versionDto) {
		if (versionDto == null) {
			return null;
		} else {
			Version version = new Version();

			version.setUuid(versionDto.getUuid());
			version.setId(versionDto.getVersionId());

			DocumentAssembler documentAssembler = new DocumentAssembler();
			version.setDocument(documentAssembler.dtoToModelSimple(versionDto.getDocument()));

			version.setNumber(versionDto.getNumber());
			version.setFileName(versionDto.getFileName());
			version.setFileContent(versionDto.getFileContent());

			return version;
		}
	}

	public VersionDTO modelToDto(Version version) {
		if (version == null) {
			return null;
		} else {
			VersionDTO versionDto = new VersionDTO();

			versionDto.setUuid(version.getUuid());
			versionDto.setVersionId(version.getId());

			DocumentAssembler documentAssembler = new DocumentAssembler();
			versionDto.setDocument(documentAssembler.modelToDtoSimple(version.getDocument()));

			versionDto.setNumber(version.getNumber());
			versionDto.setFileName(version.getFileName());
			versionDto.setFileContent(version.getFileContent());

			return versionDto;
		}
	}
}
