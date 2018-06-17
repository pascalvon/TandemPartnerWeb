package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

// TODO Luis: 2018-06-17 Bitte beschreiben fuer die JavaDoc
@FacesValidator("passwordValidatorProfil")
public class PasswordValidatorProfil implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uicomponent, Object o) throws ValidatorException {
        String password = (String) o;
        String confirm = (String) uicomponent.getAttributes().get("confirm");

        if (password.isEmpty() && confirm.isEmpty())
        {
            return;
        }
        if (password.length()< 8 || password.length()>30)
        {
            throw new ValidatorException(new FacesMessage("Das Passwort muss zwischen 8 und 30 Zeichen lang sein!"));
        }
        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage("Passwörter stimmen nicht überein!"));
        }

    }

}
