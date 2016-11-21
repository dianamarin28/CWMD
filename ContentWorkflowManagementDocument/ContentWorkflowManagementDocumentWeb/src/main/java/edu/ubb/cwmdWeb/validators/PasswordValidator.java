package edu.ubb.cwmdWeb.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object pwd) throws ValidatorException {
		String password = pwd.toString();

		if (password.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Password is required");
			throw new ValidatorException(message);
		} else {
			if (password.length() < 3) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Password is too short");
				throw new ValidatorException(message);
			}
		}

		if (password.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Password is too long");
			throw new ValidatorException(message);
		}

	}

}
