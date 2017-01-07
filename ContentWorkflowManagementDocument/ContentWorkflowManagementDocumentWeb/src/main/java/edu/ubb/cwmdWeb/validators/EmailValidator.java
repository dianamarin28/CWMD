package edu.ubb.cwmdWeb.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		;
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		String name = value.toString();

		if (name.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Email is mandatory");
			throw new ValidatorException(message);
		}
		if (validateEmail(name) == false) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error","Email format not valid");
			throw new ValidatorException(message);
		}
	}

}

