package edu.ubb.cwmdWeb.service;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.ubb.cwmdEjbClient.dtos.VersionDTO;
import edu.ubb.cwmdEjbClient.interfaces.VersionBeanInterface;

@ManagedBean(name = "versionService", eager = true)
@ApplicationScoped
public class VersionService {

	@EJB
	private VersionBeanInterface versionBeanInterface;

	public VersionDTO getVersion(Long id) {
		return versionBeanInterface.findById(id);
	}

}
