package edu.ubb.cwmdWeb.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("userNameValidator")
public class UserNameValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object userName) throws ValidatorException {
		String userNameValidate = userName.toString();

		if (userNameValidate.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "User name is mandatory.");
			throw new ValidatorException(message);
		}

		if (userNameValidate.length() < 5) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "User name is too short");
			throw new ValidatorException(message);
		}

		if (userNameValidate.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "User name is too long");
			throw new ValidatorException(message);
		}

		if (userNameValidate.contains(" ")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "User name is invalid.");
			throw new ValidatorException(message);
		}
	}
}