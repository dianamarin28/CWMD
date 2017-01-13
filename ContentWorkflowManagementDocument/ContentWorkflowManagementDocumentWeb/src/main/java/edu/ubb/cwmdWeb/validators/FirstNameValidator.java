package edu.ubb.cwmdWeb.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import javax.faces.application.FacesMessage;

@FacesValidator("firstNameValidator")
public class FirstNameValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object firstName) throws ValidatorException {
		String firstNameValidate = firstName.toString();

		if (firstNameValidate.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "First name is mandatory.");
			throw new ValidatorException(message);
		}

		if (firstNameValidate.length() < 3) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "First name is too short");
			throw new ValidatorException(message);
		}

		if (firstNameValidate.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "First name is too long");
			throw new ValidatorException(message);
		}

	}

}