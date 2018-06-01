package validators;

import dao.DAO;
import models.Nutzer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

@ManagedBean
@RequestScoped
public class MailValidatorRegistrieren implements Validator {

    @EJB
    private DAO dao ;
    private String mail;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        mail = (String)o;
        boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();


        if(mail.isEmpty()){
            return;
        } else if(!dao.findNutzerByMailBoolean(mail)) {
            throw new ValidatorException((new FacesMessage("Diese E-Mail-Adresse wird bereits verwendet!")));
        } else if(!matchesPattern) {
            throw new ValidatorException((new FacesMessage("Ungültige E-Mail-Adresse", "Bitte erneut eingeben")));
        }
    }
}
