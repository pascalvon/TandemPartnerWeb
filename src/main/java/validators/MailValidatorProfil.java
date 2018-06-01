package validators;

import dao.DAO;
import jsfbeans.LoginManagedBean;
import models.Nutzer;

import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mailValidatorProfil")
public class MailValidatorProfil implements Validator {

    @EJB
    private DAO dao = new DAO();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String mail = (String)value;


    }
}
