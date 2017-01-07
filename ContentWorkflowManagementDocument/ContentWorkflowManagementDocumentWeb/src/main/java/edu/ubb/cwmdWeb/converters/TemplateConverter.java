package edu.ubb.cwmdWeb.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.ubb.cwmdEjbClient.dtos.TemplateDTO;
import edu.ubb.cwmdWeb.service.TemplateService;

@FacesConverter("templateConverter")
public class TemplateConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				TemplateService service = (TemplateService) fc.getExternalContext().getApplicationMap()
						.get("templateService");
				return service.getTemplate(Long.parseLong(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid template."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((TemplateDTO) object).getTemplateId());
		} else {
			return null;
		}
	}

}
