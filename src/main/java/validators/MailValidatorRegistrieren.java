package validators;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@FacesValidator("mailValidatorRegistrieren")
public class MailValidatorRegistrieren implements Validator {

    @EJB
    private DAO dao;
    private String mail;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        mail = (String)o;
        //boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();

        if(dao.findNutzerByMailBoolean(mail)){
            throw new ValidatorException(new FacesMessage("mail alredy used "));
        }
//        else
//        {
//            throw new ValidatorException((new FacesMessage("Invalid mail "+mail)));
//        }
//        if(!matchesPattern)
//        {
//            throw new ValidatorException((new FacesMessage("Invalid mail")));
//        }


    }


// private boolean validateNutzer(String mail) {
//            return dao.findNutzerByMailBoolean(mail);
//
//    }
}
