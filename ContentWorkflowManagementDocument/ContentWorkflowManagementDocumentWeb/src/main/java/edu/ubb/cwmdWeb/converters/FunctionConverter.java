package edu.ubb.cwmdWeb.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.ubb.cwmdEjbClient.dtos.FunctionDTO;
import edu.ubb.cwmdWeb.service.FunctionService;

@FacesConverter("functionConverter")
public class FunctionConverter implements Converter{
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				FunctionService service = (FunctionService) fc.getExternalContext().getApplicationMap().get("functionService");
				return service.getFunction(Long.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid function."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((FunctionDTO) object).getFunctionId());
		} else {
			return null;
		}
	}

}
