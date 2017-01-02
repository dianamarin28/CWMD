package edu.ubb.cwmdWeb.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdWeb.service.ActiveFlowService;

@FacesConverter("activeFlowConverter")
public class ActiveFlowConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				ActiveFlowService service = (ActiveFlowService) fc.getExternalContext().getApplicationMap()
						.get("activeFlowService");
				return service.getActiveFlow(Long.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid active flow."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((ActiveFlowDTO) object).getActiveFlowId());
		} else {
			return null;
		}
	}

}
