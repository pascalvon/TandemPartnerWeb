package validators;

import jsfbeans.LoginManagedBean;
import models.Nutzer;
import utilities.HashedPasswordGenerator;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("deleteValidator")
public class DeleteValidator implements Validator {

    private Nutzer nutzer;

    public DeleteValidator(){
        initNutzer();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String password = HashedPasswordGenerator.generateHash((String) o);


        if(!password.equals(nutzer.getPasswort())){
            throw new ValidatorException(new FacesMessage("Falsches Passwort!", "Profil konnte nicht gelöscht werden!"));
        }
        return;
    }

    private void initNutzer() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginManagedBean loginManagedBean = (LoginManagedBean) elContext.getELResolver().getValue(elContext, null, "loginManagedBean");
        nutzer = loginManagedBean.nutzer;
    }
}
