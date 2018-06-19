package validators;

import dao.DAO;
import jsfbeans.LoginManagedBean;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

// TODO Luis: 2018-06-17 Bitte beschreiben fuer die JavaDoc
@ManagedBean
@RequestScoped
public class MailValidatorProfil implements Validator {

    @EJB
    private DAO dao;
    private Nutzer nutzer;
    private String mail;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public MailValidatorProfil()
    {
        initNutzer();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object o) throws ValidatorException {
        mail = (String)o;
        boolean matchesPattern = EMAIL_PATTERN.matcher(mail).find();

        if(mail.isEmpty() || mail.equals(nutzer.getMail())){
            return;
        }
        else if(!dao.findNutzerByMailBoolean(mail)) {
            throw new ValidatorException((new FacesMessage("Diese E-Mail-Adresse wird bereits verwendet!")));
        } else if(!matchesPattern) {
            throw new ValidatorException((new FacesMessage("Ungültige E-Mail-Adresse", "Bitte erneut eingeben")));
        }
    }


    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.getNutzer();
    }

    public Nutzer getNutzer() {
        return nutzer;
    }

    public void setNutzer(Nutzer nutzer) {
        this.nutzer = nutzer;
    }
}
