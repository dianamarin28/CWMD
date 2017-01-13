package edu.ubb.cwmdWeb.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("lastNameValidator")
public class LastNameValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object lastName) throws ValidatorException {
		String lastNameValidate = lastName.toString();

		if (lastNameValidate.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Last name is mandatory.");
			throw new ValidatorException(message);
		}

		if (lastNameValidate.length() < 3) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Last name is too short");
			throw new ValidatorException(message);
		}

		if (lastNameValidate.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Last name is too long");
			throw new ValidatorException(message);
		}
	}

}