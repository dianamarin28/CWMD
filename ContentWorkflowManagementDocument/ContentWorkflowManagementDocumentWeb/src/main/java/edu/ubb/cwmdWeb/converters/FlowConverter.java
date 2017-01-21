package edu.ubb.cwmdWeb.converters;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.ubb.cwmdEjbClient.dtos.ActiveFlowDTO;
import edu.ubb.cwmdEjbClient.dtos.FlowDTO;
import edu.ubb.cwmdWeb.service.FlowService;

@FacesConverter("flowConverter")
public class FlowConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				FlowService service = (FlowService) fc.getExternalContext().getApplicationMap()
						.get("flowService");
				return service.getFlow(Long.parseLong(value));
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
			return String.valueOf(((FlowDTO) object).getFlowId());
		} else {
			return null;
		}
	}

}
